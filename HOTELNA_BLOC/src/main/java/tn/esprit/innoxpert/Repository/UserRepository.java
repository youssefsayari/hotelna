package tn.esprit.innoxpert.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.innoxpert.Entity.TypeUser;
import tn.esprit.innoxpert.Entity.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:searchTerm% OR u.lastName LIKE %:searchTerm%")
    List<User> searchByName(@Param("searchTerm") String searchTerm);





}
