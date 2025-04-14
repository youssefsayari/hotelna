package tn.esprit.innoxpert.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.innoxpert.Entity.ComplaintSolutionIA;

import java.util.Optional;

public interface ComplaintSolutionIARepository extends JpaRepository<ComplaintSolutionIA, Long> {
    Optional<ComplaintSolutionIA> findByComplaintId(Long complaintId);
}
