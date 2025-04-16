package tn.esprit.innoxpert.Service;

import tn.esprit.innoxpert.Entity.Complaint;
import tn.esprit.innoxpert.Entity.ComplaintStatus;

import java.util.List;

public interface ComplaintServiceInterface {
    Complaint createComplaint(Complaint complaint, Long userId);
    Complaint getComplaintById(Long id);
    List<Complaint> getComplaintsByStatus(ComplaintStatus status);
     List<Complaint> getAllComplaints();
    List<Complaint> getComplaintsByUser(Long userId);
    Complaint updateComplaint(Long id, Complaint complaintDetails);
    void deleteComplaint(Long id);
}
