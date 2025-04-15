package tn.esprit.innoxpert.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.Dto.UserResponseDTO;
import tn.esprit.innoxpert.Entity.Chambre;
import tn.esprit.innoxpert.Entity.ChambreReservation;
import tn.esprit.innoxpert.Entity.EtatChambre;
import tn.esprit.innoxpert.Exceptions.NotFoundException;
import tn.esprit.innoxpert.Repository.ChambreRepository;
import tn.esprit.innoxpert.Util.UserServiceClient;

import java.util.List;

@Service
@AllArgsConstructor
public class ChambreService implements ChambreServiceInterface {

    private final ChambreRepository chambreRepository;
    private final UserServiceClient userServiceClient;


    @Override
    public Chambre ajouterChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public Chambre modifierChambre(Chambre chambre) {
        if (!chambreRepository.existsById(chambre.getIdChambre())) {
            throw new NotFoundException("Chambre avec ID " + chambre.getIdChambre() + " introuvable.");
        }
        return chambreRepository.save(chambre);
    }

    @Override
    public void supprimerChambre(Long idChambre) {
        chambreRepository.deleteById(idChambre);
    }

    @Override
    public Chambre getChambreById(Long idChambre) {
        return chambreRepository.findById(idChambre)
                .orElseThrow(() -> new NotFoundException("Chambre avec ID " + idChambre + " introuvable."));
    }

    @Override
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    @Override
    public List<Chambre> getChambresParEtat(EtatChambre etat) {
        return chambreRepository.findByEtat(etat);
    }
    @Override
    public Chambre changerEtatChambre(Long idChambre, EtatChambre nouvelEtat) {
        Chambre chambre = chambreRepository.findById(idChambre)
                .orElseThrow(() -> new NotFoundException("Chambre avec ID " + idChambre + " introuvable."));
        chambre.setEtat(nouvelEtat);
        return chambreRepository.save(chambre);
    }

    @Override
    public void nettoyerChambresUtilisateur(Long idUser) {
        List<Chambre> chambres = chambreRepository.findAll(); // Ou mieux, créer une méthode personnalisée
        chambres.stream()
                .filter(chambre ->  chambre.getUserId().equals(idUser))
                .forEach(chambre -> {
                    chambre.setEtat(EtatChambre.EN_NETTOYAGE);
                    chambreRepository.save(chambre);
                });
    }
    @Override
    public Chambre reserverChambre(Long idChambre, Long idUser) {
        Chambre chambre = chambreRepository.findById(idChambre)
                .orElseThrow(() -> new NotFoundException("Chambre non trouvée"));

        if (chambre.getChambreEtat() == ChambreReservation.RESERVEE) {
            throw new IllegalStateException("❌ Cette chambre est déjà réservée !");
        }

        UserResponseDTO user = userServiceClient.getUserById(idUser);

if (user!=null) {
    chambre.setChambreEtat(ChambreReservation.RESERVEE);
    chambre.setEtat(EtatChambre.OCCUPEE);
    chambre.setUserId(idUser);
}
        return chambreRepository.save(chambre);

    }

}
