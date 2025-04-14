package tn.esprit.innoxpert.Exceptions;

public class ComplaintNotFoundException extends NotFoundException {
    public ComplaintNotFoundException(Long complaintId) {
        super("Complaint not found with ID: " + complaintId);
    }
}
