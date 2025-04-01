package tn.esprit.innoxpert.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.Entity.Complaint;
import tn.esprit.innoxpert.Entity.User;
import tn.esprit.innoxpert.Exceptions.ComplaintNotFoundException;
import tn.esprit.innoxpert.Exceptions.UserNotFoundException;
import tn.esprit.innoxpert.Repository.ComplaintRepository;
import tn.esprit.innoxpert.Repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ComplaintService implements ComplaintServiceInterface {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    // CREATE
    public Complaint createComplaint(Complaint complaint, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            complaint.setUser(user);
            return complaintRepository.save(complaint);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la plainte : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // READ
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

    public List<Complaint> getAllComplaints() {
        try {
            return complaintRepository.findAll(Sort.by(Sort.Direction.ASC, "complaintDate"));
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de toutes les plaintes : " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    // UPDATE
    public Complaint updateComplaint(Long id, Complaint complaintDetails) {
        try {
            Complaint complaint = getComplaintById(id);
            if (complaint == null) return null;

            // Mise à jour des champs modifiables
            if (complaintDetails.getDescription() != null) {
                complaint.setDescription(complaintDetails.getDescription());
            }
            if (complaintDetails.getCategory() != null) {
                complaint.setCategory(complaintDetails.getCategory());
            }
            if (complaintDetails.getStatus() != null) {
                complaint.setStatus(complaintDetails.getStatus());
            }
            if (complaintDetails.getResolutionDetails() != null) {
                complaint.setResolutionDetails(complaintDetails.getResolutionDetails());
            }

            return complaintRepository.save(complaint);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de la plainte : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // DELETE
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