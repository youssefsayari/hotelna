package tn.esprit.innoxpert.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"complaints", "hibernateLazyInitializer", "handler"})
    private User user;

     LocalDateTime complaintDate = LocalDateTime.now();
     String description;
    @Enumerated(EnumType.STRING)
    ComplaintStatus status = ComplaintStatus.OUVERT;
    @Enumerated(EnumType.STRING)
    ComplaintCategories category;
     LocalDateTime resolutionDate;
     String resolutionDetails;
}
