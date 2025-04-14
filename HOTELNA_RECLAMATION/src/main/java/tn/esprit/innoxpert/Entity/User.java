package tn.esprit.innoxpert.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    /*--------------------------------Start Sayari-----------------------------------*/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Complaint> complaints = new ArrayList<>();
    /*--------------------------------End Sayari-----------------------------------*/





}
