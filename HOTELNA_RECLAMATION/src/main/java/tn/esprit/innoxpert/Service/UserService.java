package tn.esprit.innoxpert.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import tn.esprit.innoxpert.Entity.User;
import tn.esprit.innoxpert.Exceptions.NotFoundException;
import tn.esprit.innoxpert.Repository.UserRepository;
import tn.esprit.innoxpert.Util.EmailClass;

import java.util.*;




@Service
@AllArgsConstructor
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;



    private final EmailClass emailClass = new EmailClass();
    //private final Random random = new Random();



    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long UserId) {
        return userRepository.findById(UserId)
                .orElseThrow(() -> new NotFoundException("User with ID : " + UserId + " was not found."));
    }











    @Override
    public User addUser(User b) {
        return userRepository.save(b);
    }

    @Override
    public void removeUserById(Long UserId) {
        if (!userRepository.existsById(UserId)) {
            throw new NotFoundException("User with ID :  " + UserId + " was not found.");
        }
        userRepository.deleteById(UserId);
    }

    @Override
    public User updateUser(User b) {
        if (!userRepository.existsById(b.getIdUser())) {
            throw new NotFoundException("User with ID: " + b.getIdUser() + " was not found. Cannot update.");
        }
        return userRepository.save(b);
    }






    @Override
    public String generateOtp(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return "❌ User not found!";
        }

        User user = userOptional.get();
        long otp = 100000 + (long) (Math.random() * 900000);

        user.setOTP(otp);
        userRepository.save(user);

        emailClass.sendOtpEmail(user.getEmail(), otp);

        return "✅ OTP sent successfully to " + email;
    }


    @Override
    public boolean validateOtp(String email, Long enteredOtp) {
        Boolean response;
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        response = user.getOTP() != null && user.getOTP().equals(enteredOtp);

        if (response) {
            user.setOTP(null);
            userRepository.save(user);
        }

        return response;
    }

    @Override
    public boolean changePassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        user.setPassword(newPassword);
        userRepository.save(user);

        return true;
    }
    public User login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("❌ User not found with email: " + email);
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("❌ Incorrect password!");
        }

        return user;
    }




}
