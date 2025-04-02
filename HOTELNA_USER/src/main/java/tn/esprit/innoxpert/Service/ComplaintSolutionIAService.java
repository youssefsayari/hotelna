package tn.esprit.innoxpert.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.Entity.Complaint;
import tn.esprit.innoxpert.Entity.ComplaintSolutionIA;
import tn.esprit.innoxpert.Entity.ComplaintStatus;
import tn.esprit.innoxpert.Repository.ComplaintRepository;
import tn.esprit.innoxpert.Repository.ComplaintSolutionIARepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintSolutionIAService {
    private final ComplaintSolutionIARepository complaintSolutionIARepository;
    private final ComplaintRepository complaintRepository;

    @Transactional
    public Optional<ComplaintSolutionIA> getSolutionByComplaint(long complaintId) {
        return complaintSolutionIARepository.findByComplaintId(complaintId);
    }

    @Transactional
    public ComplaintSolutionIA applySolution(long complaintId,ComplaintSolutionIA solutionIA) {
        Complaint complaint = complaintRepository.getReferenceById(complaintId);
        complaint.setStatus(ComplaintStatus.EN_PROGRESSION);
        solutionIA.setComplaint(complaint);
        return complaintSolutionIARepository.save(solutionIA);
    }
}