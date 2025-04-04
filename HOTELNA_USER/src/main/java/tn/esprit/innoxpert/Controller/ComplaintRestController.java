package tn.esprit.innoxpert.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tn.esprit.innoxpert.Entity.Complaint;
import tn.esprit.innoxpert.Entity.ComplaintCategories;
import tn.esprit.innoxpert.Entity.ComplaintSolutionIA;
import tn.esprit.innoxpert.Entity.ComplaintStatus;
import tn.esprit.innoxpert.Service.ComplaintService;
import tn.esprit.innoxpert.Service.ComplaintSolutionIAService;
import tn.esprit.innoxpert.Util.MistralAIService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Complaint Management")
@RestController
@AllArgsConstructor
@RequestMapping("/complaint")
public class ComplaintRestController {
    @Autowired
    private final ComplaintService complaintService;

    private final ComplaintSolutionIAService complaintSolutionIAService;

    // CREATE
    @PostMapping("/addComplaint")
    public ResponseEntity<?> createComplaint(
            @Valid @RequestBody Complaint complaint,
            BindingResult result,
            @RequestParam Long userId) {

        if (result.hasErrors()) {
            return buildValidationErrorResponse(result);
        }

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
    @GetMapping("/getComplaintsByStatus/{status}")
    public ResponseEntity<?> getComplaintsByStatus(@PathVariable ComplaintStatus status) {
        try {
            List<Complaint> complaints = complaintService.getComplaintsByStatus(status);
            return ResponseEntity.ok(complaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to retrieve complaints by status",
                            "details", e.getMessage()
                    ));
        }
    }
    @GetMapping("/getComplaintsByUser/{userId}")
    public ResponseEntity<?> getComplaintsByUser(@PathVariable Long userId) {
        try {
            List<Complaint> complaints = complaintService.getComplaintsByUser(userId);
            return ResponseEntity.ok(complaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to retrieve user complaints",
                            "details", e.getMessage()
                    ));
        }
    }

    // UPDATE
    @PutMapping("/updateComplaint/{id}")
    public ResponseEntity<?> updateComplaint(
            @PathVariable Long id,
            @Valid @RequestBody Complaint complaint,
            BindingResult result) {

        if (result.hasErrors()) {
            // Retournez les erreurs de validation au frontend
            List<String> errors = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error", "Validation failed",
                            "details", errors
                    ));
        }

        try {
            Complaint updatedComplaint = complaintService.updateComplaint(id, complaint);
            return ResponseEntity.ok(updatedComplaint);
        } catch (IllegalArgumentException e) {
            // Gestion spécifique des erreurs métier
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error", "Business rule violation",
                            "message", e.getMessage()
                    ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Internal server error",
                            "message", e.getMessage()
                    ));
        }
    }

    // DELETE

    @DeleteMapping("/deleteComplaint/{id}")
    public ResponseEntity<Map<String, Object>> deleteComplaint(@PathVariable Long id) {
        if (complaintService.getComplaintById(id)==null) { // Vérification avant suppression
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", "Réclamation introuvable",
                            "id", id,
                            "details", "Aucune réclamation trouvée avec l'ID " + id
                    ));
        }

        try {
            complaintService.deleteComplaint(id);
            return ResponseEntity.ok(Map.of(
                    "message", "La réclamation a été supprimée avec succès.",
                    "id", id
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Échec de la suppression",
                            "id", id,
                            "details", e.getMessage()
                    ));
        }
    }



    // Méthode utilitaire pour gérer les erreurs de validation
    private ResponseEntity<Map<String, Object>> buildValidationErrorResponse(BindingResult result) {
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest()
                .body(Map.of(
                        "error", "Validation failed",
                        "messages", errors
                ));
    }

                                /*------------partie ia ----------------*/
        private final MistralAIService mistralAIService;

    @PostMapping("/generate-ai-solution")
    public ResponseEntity<Map<String, Object>> generateAiSolution(
            @RequestParam ComplaintCategories category,
            @RequestParam String description) {

        Map<String, Object> response = mistralAIService.generateInitialSolution(category, description);

        HttpStatus status = switch ((String) response.get("status")) {
            case "error", "format_error" -> HttpStatus.INTERNAL_SERVER_ERROR;
            case "invalid" -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.OK;
        };

        return new ResponseEntity<>(response, status);
    }
    @GetMapping("getSolutionByComplaint/{complaintId}")
    public ResponseEntity<Optional<ComplaintSolutionIA>> getSolution(@PathVariable Long complaintId) {
        return ResponseEntity.ok(complaintSolutionIAService.getSolutionByComplaint(complaintId));
    }

    @PostMapping("acceptSolutionAndAffectToComplaint/{complaintId}")
    public ResponseEntity<Void> acceptSolution(@RequestBody ComplaintSolutionIA solutionIA,@PathVariable Long complaintId) {
        complaintSolutionIAService.applySolution(complaintId,solutionIA);
        return ResponseEntity.ok().build();
    }
}