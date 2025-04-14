package tn.esprit.innoxpert.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintSolutionIA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String analyseReclamation;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String solutionProposee;

    @Column(columnDefinition = "TEXT")
    private String compensation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false, unique = true)
    @JsonBackReference
    private Complaint complaint;
}