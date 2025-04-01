package tn.esprit.innoxpert.Service;

import tn.esprit.innoxpert.Entity.Complaint;

public interface ComplaintServiceInterface {
    Complaint createComplaint(Complaint complaint, Long userId);
    Complaint getComplaintById(Long id);
}
