package tn.esprit.innoxpert.Service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.Entity.Complaint;
import tn.esprit.innoxpert.Entity.ComplaintStatus;
import tn.esprit.innoxpert.Entity.User;
import tn.esprit.innoxpert.Exceptions.ComplaintNotFoundException;
import tn.esprit.innoxpert.Exceptions.UserNotFoundException;
import tn.esprit.innoxpert.Repository.ComplaintRepository;
import tn.esprit.innoxpert.Repository.UserRepository;
import tn.esprit.innoxpert.Util.EmailClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ComplaintService implements ComplaintServiceInterface {
    @Autowired
    private final ComplaintRepository complaintRepository;
    @Autowired
    private final UserRepository userRepository;

    private final EmailClass emailClass;

    // CREATE
    @Override
    public Complaint createComplaint(@Valid Complaint complaint, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));

            complaint.setUser(user);

            // Validation métier supplémentaire
            validateComplaintBusinessRules(complaint);
/*------------------------------------------SEND EMAIL----------------------------------*/
            Complaint savedComplaint = complaintRepository.save(complaint);
            // Notification de création
            emailClass.sendHtmlEmail(
                    user.getEmail(),
                    "📬 Votre réclamation a été enregistrée",
                    String.format(
                            "<html><body style='padding: 30px; font-family: Arial;'>" +
                                    "<h2 style='color: #3498db;'>Merci pour votre réclamation !</h2>" +
                                    "<p>Nous avons bien reçu votre demande (#%d) et la traiterons dans les plus brefs délais.</p>" +
                                    "<p>Vous pouvez suivre son avancement depuis votre espace personnel.</p>" +
                                    "</body></html>",
                            savedComplaint.getId()
                    )
            );

            return savedComplaint;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la plainte: " + e.getMessage(), e);
        }
    }

    // READ
    @Override
    public Complaint getComplaintById(Long id) {
        try {
            return complaintRepository.findById(id)
                    .orElseThrow(() -> new ComplaintNotFoundException(id));
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de la plainte : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Complaint> getAllComplaints() {
        try {
            return complaintRepository.findAll(Sort.by(Sort.Direction.ASC, "complaintDate"));
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de toutes les plaintes : " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    // Ajoutez cette méthode dans votre service
    @Override
    public List<Complaint> getComplaintsByStatus(ComplaintStatus status) {
        try {
            return complaintRepository.findByStatus(status);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des plaintes par statut : " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    @Override
    public List<Complaint> getComplaintsByUser(Long userId) {
        try {
            return complaintRepository.findByUser_IdUser(userId);
        } catch (Exception e) {
            System.err.println("Error fetching complaints for user " + userId + ": " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    // UPDATE
    @Override
    public Complaint updateComplaint(Long id, Complaint complaintDetails) {
        Complaint complaint = getComplaintById(id);
        ComplaintStatus oldStatus = complaint.getStatus();

        if (complaint == null) {
            throw new IllegalArgumentException("Complaint not found with id: " + id);
        }

        // Validation métier
        if (complaintDetails.getStatus() == ComplaintStatus.RESOLU) {
            if (complaintDetails.getResolutionDetails() == null || complaintDetails.getResolutionDetails().trim().isEmpty()) {
                throw new IllegalArgumentException("Resolution details are required when status is RESOLU");
            }
        }

        // Mise à jour des champs
        complaint.setDescription(complaintDetails.getDescription());
        complaint.setCategory(complaintDetails.getCategory());
        complaint.setStatus(complaintDetails.getStatus());
        complaint.setResolutionDetails(complaintDetails.getResolutionDetails());

        if (complaintDetails.getStatus() == ComplaintStatus.RESOLU) {
            complaint.setResolutionDate(LocalDateTime.now());
        }

        Complaint updatedComplaint = complaintRepository.save(complaint);

        // Envoyer la notification si le statut a changé
        if (!Objects.equals(oldStatus, updatedComplaint.getStatus())) {
            User user = updatedComplaint.getUser();

            emailClass.sendComplaintStatusUpdate(
                    user.getEmail(),
                    user.getFirstName(),
                    updatedComplaint,  // Passer l'objet Complaint complet
                    oldStatus.name(),
                    String.valueOf(updatedComplaint.getStatus())   // Passer l'ancien statut
            );
        }

        return updatedComplaint;
    }


    // Validation des règles métier
    private void validateComplaintBusinessRules(Complaint complaint) {
        if (complaint.getStatus() == ComplaintStatus.RESOLU &&
                (complaint.getResolutionDetails() == null || complaint.getResolutionDetails().trim().isEmpty())) {
            throw new IllegalArgumentException("Les détails de résolution sont obligatoires pour un statut RESOLU");
        }
    }

    // DELETE
    @Override
    public void deleteComplaint(Long id) {
        try {
            Complaint complaint = getComplaintById(id);
            if (complaint != null) {
                complaintRepository.delete(complaint);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de la plainte : " + e.getMessage());
            e.printStackTrace();
        }
    }
}