package tn.esprit.innoxpert.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.innoxpert.Entity.Bloc;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {
}
