package tn.esprit.innoxpert.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
public class ParticipationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Long telephone;
    private Long activityId;
    private Long userId;

}
