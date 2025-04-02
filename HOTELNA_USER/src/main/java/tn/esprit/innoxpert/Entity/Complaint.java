package tn.esprit.innoxpert.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "La date de réclamation ne peut pas être nulle")
    LocalDateTime complaintDate = LocalDateTime.now();

    @NotBlank(message = "La description ne peut pas être vide")
    @Size(min = 10, max = 1000, message = "La description doit contenir entre 10 et 1000 caractères")
    @Column(length = 1000)
    String description;

    @NotNull(message = "Le statut ne peut pas être nul")
    @Enumerated(EnumType.STRING)
    ComplaintStatus status = ComplaintStatus.OUVERT;

    @NotNull(message = "La catégorie ne peut pas être nulle")
    @Enumerated(EnumType.STRING)
    ComplaintCategories category;

    LocalDateTime resolutionDate;

    @Size(max = 2000, message = "Les détails de résolution ne peuvent pas dépasser 2000 caractères")
    @Column(length = 2000)
    String resolutionDetails;
}