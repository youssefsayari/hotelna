package tn.esprit.innoxpert.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idChambre;
    boolean disponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloc_id", referencedColumnName = "idBloc")
    @JsonIgnore
    private Bloc bloc;
}
