package tn.esprit.innoxpert.Service;

import tn.esprit.innoxpert.Entity.Chambre;
import tn.esprit.innoxpert.Entity.EtatChambre;

import java.util.List;

public interface ChambreServiceInterface {
    Chambre ajouterChambre(Chambre chambre);
    Chambre modifierChambre(Chambre chambre);
    void supprimerChambre(Long idChambre);
    Chambre getChambreById(Long idChambre);
    List<Chambre> getAllChambres();
    List<Chambre> getChambresParEtat(EtatChambre etat);
    Chambre changerEtatChambre(Long idChambre, EtatChambre nouvelEtat);
    void nettoyerChambresUtilisateur(Long idUser);
    Chambre reserverChambre(Long idChambre, Long idUser);


}
