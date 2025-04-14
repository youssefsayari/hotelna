package tn.esprit.innoxpert.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Dans le User Service
@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    // Getters/Setters
}