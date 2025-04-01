package tn.esprit.innoxpert.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.innoxpert.Entity.Complaint;
import tn.esprit.innoxpert.Service.ComplaintService;

import java.util.List;
import java.util.Map;

@Tag(name = "Complaint Management")
@RestController
@AllArgsConstructor
@RequestMapping("/complaint")
public class ComplaintRestController {

    private final ComplaintService complaintService;

    // CREATE
    @PostMapping("/addComplaint")
    public ResponseEntity<?> createComplaint(
            @RequestBody Complaint complaint,
            @RequestParam Long userId) {
        try {
            Complaint createdComplaint = complaintService.createComplaint(complaint, userId);
            return new ResponseEntity<>(createdComplaint, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "error", "Failed to create complaint",
                            "details", e.getMessage()
                    ));
        }
    }

    // READ
    @GetMapping("/getComplaintById/{id}")
    public ResponseEntity<?> getComplaint(@PathVariable Long id) {
        try {
            Complaint complaint = complaintService.getComplaintById(id);
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", "Complaint not found",
                            "details", e.getMessage()
                    ));
        }
    }

    @GetMapping("/getAllComplaints")
    public ResponseEntity<?> getAllComplaints() {
        try {
            List<Complaint> complaints = complaintService.getAllComplaints();
            return ResponseEntity.ok(complaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to retrieve complaints",
                            "details", e.getMessage()
                    ));
        }
    }

    // UPDATE
    @PutMapping("/updateComplaint/{id}")
    public ResponseEntity<?> updateComplaint(@PathVariable Long id, @RequestBody Complaint complaintDetails) {
        try {
            Complaint updatedComplaint = complaintService.updateComplaint(id, complaintDetails);
            return ResponseEntity.ok(updatedComplaint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "error", "Failed to update complaint",
                            "details", e.getMessage()
                    ));
        }
    }

    // DELETE
    @DeleteMapping("/deleteComplaint/{id}")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long id) {
        try {
            complaintService.deleteComplaint(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to delete complaint",
                            "details", e.getMessage()
                    ));
        }
    }
}