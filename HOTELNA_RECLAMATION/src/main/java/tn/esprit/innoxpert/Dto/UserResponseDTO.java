package tn.esprit.innoxpert.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;

    // ... autres champs (sans OTP/password)
}
