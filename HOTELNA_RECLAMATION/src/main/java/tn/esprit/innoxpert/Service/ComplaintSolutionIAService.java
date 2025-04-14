package tn.esprit.innoxpert.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.Entity.Complaint;
import tn.esprit.innoxpert.Entity.ComplaintSolutionIA;
import tn.esprit.innoxpert.Entity.ComplaintStatus;
import tn.esprit.innoxpert.Entity.User;
import tn.esprit.innoxpert.Exceptions.ComplaintNotFoundException;
import tn.esprit.innoxpert.Repository.ComplaintRepository;
import tn.esprit.innoxpert.Repository.ComplaintSolutionIARepository;
import tn.esprit.innoxpert.Util.EmailClass;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintSolutionIAService {
    private final ComplaintSolutionIARepository complaintSolutionIARepository;
    private final ComplaintRepository complaintRepository;
    private final EmailClass emailClass;


    @Transactional
    public Optional<ComplaintSolutionIA> getSolutionByComplaint(long complaintId) {
        return complaintSolutionIARepository.findByComplaintId(complaintId);
    }

    public ComplaintSolutionIA applySolution(long complaintId, ComplaintSolutionIA solutionIA) {
        // 1. Charger l'entité Complaint avec la session
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException(complaintId));

        // 2. Sauvegarder d'abord la solution
        solutionIA.setComplaint(complaint); // Établir la relation bidirectionnelle
        ComplaintSolutionIA savedSolution = complaintSolutionIARepository.save(solutionIA);

        // 3. Mettre à jour la plainte
        ComplaintStatus oldStatus = complaint.getStatus();
        complaint.setStatus(ComplaintStatus.EN_PROGRESSION);
        complaint.setComplaintSolutionIA(savedSolution); // Utiliser l'entité sauvegardée
        complaintRepository.save(complaint); // Mettre à jour la plainte

        // 4. Envoyer l'email
        User user = complaint.getUser();
        emailClass.sendComplaintStatusUpdate(
                user.getEmail(),
                user.getFirstName(),
                complaint,
                oldStatus.name(),
                complaint.getStatus().name()
        );

        return savedSolution;
    }
}