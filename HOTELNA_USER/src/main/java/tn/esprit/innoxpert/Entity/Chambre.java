package tn.esprit.innoxpert.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chambre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idChambre;

    @Enumerated(EnumType.STRING)
    TypeChambre typeChambre; // SIMPLE, DOUBLE, SUITE

    int numero; // Exemple: 101, 202

    @Enumerated(EnumType.STRING)
    EtatChambre etat;
    @Enumerated(EnumType.STRING)
    ChambreReservation chambreEtat = ChambreReservation.NON_RESERVEE; // Valeur par défaut
// DISPONIBLE, OCCUPEE, EN_NETTOYAGE

    int etage; // Etage de la chambre

    @ManyToOne
    @JsonIgnore
    User user; // Utilisateur actuellement associé à la chambre
}
