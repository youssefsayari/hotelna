package tn.esprit.innoxpert.Exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long userId) {
        super("User not found with ID: " + userId);
    }
}
