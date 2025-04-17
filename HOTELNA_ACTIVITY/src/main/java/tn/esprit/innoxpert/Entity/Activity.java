package tn.esprit.innoxpert.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    LocalDate startDate;
    LocalTime startTime;

    String description;

    Double price;
    int capacity;

    @Enumerated(EnumType.STRING)
    TypeActivity typeActivity;

    @ElementCollection
    Set<Long> userIds = new HashSet<>();

    public boolean isExpired() {
        LocalDate expirationDate = startDate.plusDays(1);
        LocalTime expirationTime = startTime;
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        if (nowDate.isAfter(expirationDate)) {
            return true;
        } else if (nowDate.isEqual(expirationDate)) {
            return nowTime.isAfter(expirationTime);
        }
        return false;
    }


}
