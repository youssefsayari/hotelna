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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "\"user\"") // Note the escaped double quotes

public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long idUser;
     String firstName;
     String lastName;
     String password;
     String email;
     Long telephone;
    @Column(nullable = true)
    Long OTP;
    @Enumerated(EnumType.STRING)
    TypeUser typeUser;








}
