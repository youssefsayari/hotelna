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

    @PostMapping("/participate/{idactivity}/{iduser}")
    public ResponseEntity<String> participate(@PathVariable Long idactivity, @PathVariable Long iduser) {
        // Call the service method to handle participation logic
        String result = activityService.participateInActivity(idactivity, iduser);

        // If the result is "Participation successful!", send a success response
        if (result.equals("Participation successful!")) {
            return ResponseEntity.ok(result);
        }

        // If there's any other result (error or validation message), return a bad request with the message
        return ResponseEntity.badRequest().body(result);
    }


    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getActivityStatistics() {
        Map<String, Object> stats = activityService.getActivityStatistics();
        return ResponseEntity.ok(stats);
    }

}
