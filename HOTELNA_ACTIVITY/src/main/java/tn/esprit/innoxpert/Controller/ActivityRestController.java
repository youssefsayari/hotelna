package tn.esprit.innoxpert.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.innoxpert.DTO.ParticipationRequest;
import tn.esprit.innoxpert.Entity.Activity;
import tn.esprit.innoxpert.Service.ActivityServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Activity Management")
@RestController
@AllArgsConstructor
@RequestMapping("/activity")
public class ActivityRestController {

    private final ActivityServiceInterface activityService;

    @GetMapping("/getActivityById/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getActivityById(id);
        if (activity != null) {
            return ResponseEntity.ok(activity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllActivities")
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    @PostMapping("/addActivity")
    public ResponseEntity<Activity> addActivity(@RequestBody Activity activity) {
        Activity saved = activityService.addActivity(activity);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/deleteActivity/{id}")
    public ResponseEntity<Map<String, String>> deleteActivity(@PathVariable Long id) {
        activityService.removeActivityById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Activity deleted successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateActivity/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        activity.setId(id); // Ensure the correct ID is set
        Activity updated = activityService.updateActivity(activity);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/participate")
    public ResponseEntity<String> participate(@RequestBody ParticipationRequest request) {
        String result = activityService.participateInActivity(
                request.getUserId(),
                request.getActivityId(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getTelephone()
        );

        if ("Participation réussie !".equals(result)) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getActivityStatistics() {
        Map<String, Object> stats = activityService.getActivityStatistics();
        return ResponseEntity.ok(stats);
    }

}
