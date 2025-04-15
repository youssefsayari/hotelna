package tn.esprit.innoxpert.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @ManyToMany
    @JoinTable(
            name = "user_activity",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    Set<Activity> activities = new HashSet<>();










}
