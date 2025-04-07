package tn.esprit.innoxpert.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.innoxpert.Entity.Chambre;
import tn.esprit.innoxpert.Entity.EtatChambre;
import tn.esprit.innoxpert.Service.ChambreServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/chambre")
@AllArgsConstructor
@Tag(name = "Chambre Management")
public class ChambreRestController {

    private final ChambreServiceInterface chambreService;

    @PostMapping("/add")
    public Chambre ajouterChambre(@RequestBody Chambre chambre) {
        return chambreService.ajouterChambre(chambre);
    }

    @PutMapping("/update")
    public Chambre modifierChambre(@RequestBody Chambre chambre) {
        return chambreService.modifierChambre(chambre);
    }

    @DeleteMapping("/delete/{id}")
    public void supprimerChambre(@PathVariable Long id) {
        chambreService.supprimerChambre(id);
    }

    @GetMapping("/get/{id}")
    public Chambre getChambreById(@PathVariable Long id) {
        return chambreService.getChambreById(id);
    }

    @GetMapping("/all")
    public List<Chambre> getAllChambres() {
        return chambreService.getAllChambres();
    }

    @GetMapping("/etat")
    public List<Chambre> getChambresParEtat(@RequestParam EtatChambre etat) {
        return chambreService.getChambresParEtat(etat);
    }
    @PutMapping("/changer-etat/{id}")
    public Chambre changerEtatChambre(@PathVariable Long id, @RequestParam EtatChambre nouvelEtat) {
        return chambreService.changerEtatChambre(id, nouvelEtat);
    }

    @PutMapping("/nettoyer-chambres/{idUser}")
    public void nettoyerChambresUtilisateur(@PathVariable Long idUser) {
        chambreService.nettoyerChambresUtilisateur(idUser);
    }
    @PutMapping("/reserver/{idChambre}/utilisateur/{idUser}")
    public Chambre reserverChambre(@PathVariable Long idChambre, @PathVariable Long idUser) {
        return chambreService.reserverChambre(idChambre, idUser);
    }



}
