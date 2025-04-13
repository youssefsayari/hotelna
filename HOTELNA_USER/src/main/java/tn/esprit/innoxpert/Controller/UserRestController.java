package tn.esprit.innoxpert.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.innoxpert.Entity.Spa;
import tn.esprit.innoxpert.Entity.User;
import tn.esprit.innoxpert.Service.SpaService;
import tn.esprit.innoxpert.Service.UserServiceInterface;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "User Management")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    UserServiceInterface userservice;

    @Autowired
    private SpaService spaService;

    @GetMapping("/getUserById/{idUser}")
    public User getUserById(@PathVariable("idUser") Long idUser) {
        return userservice.getUserById(idUser);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userservice.getAllUsers();
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User User) {
        return userservice.addUser(User);
    }

    @DeleteMapping("/deleteUser/{idUser}")
    public void deleteUserById(@PathVariable("idUser") Long idUser) {
        userservice.removeUserById(idUser);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User User) {
        return userservice.updateUser(User);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtp(@RequestBody String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email is required"));
        }

        String responseMessage = userservice.generateOtp(email);

        if (responseMessage.contains("❌ User not found!")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found with email: " + email));
        }

        return ResponseEntity.ok(Map.of("message", responseMessage));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Boolean> verifyOtp(@RequestParam String email, @RequestParam Long otp) {
        boolean response = userservice.validateOtp(email, otp);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestParam String email, @RequestParam String newPassword) {
        boolean isChanged = userservice.changePassword(email, newPassword);

        Map<String, String> response = new HashMap<>();
        if (isChanged) {
            response.put("message", "Password successfully changed.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "User not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        return userservice.login(email, password);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String name) {
        List<User> users = userservice.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }


    @GetMapping("/getAllSpas")
    public List<Spa> getAllSpas() {
        return spaService.fetchAllSpas();
    }

    @GetMapping("/getSpaById/{id}")
    public Spa getSpa(@PathVariable String id) {
        return spaService.fetchSpaById(id);
    }
}
