package tn.esprit.innoxpert.Service;

import tn.esprit.innoxpert.Entity.User;

import java.util.List;

public interface UserServiceInterface {
    public List<User> getAllUsers();
    public User getUserById(Long userId);
    public User addUser(User b);

    public void removeUserById(Long userId);
    public User updateUser (User b );
    public boolean validateOtp(String email, Long enteredOtp) ;
    public String generateOtp(String email);
    public boolean changePassword(String email, String newPassword) ;
    public User login(String email, String password) ;
    public List<User> searchUsersByName(String searchTerm) ;








}
