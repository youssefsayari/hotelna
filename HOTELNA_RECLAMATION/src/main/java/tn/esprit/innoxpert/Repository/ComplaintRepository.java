package tn.esprit.innoxpert.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.innoxpert.Entity.Complaint;
import tn.esprit.innoxpert.Entity.ComplaintStatus;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStatus(ComplaintStatus status);
    List<Complaint> findByUserId(Long userId); // Utiliser le champ userId direct

}