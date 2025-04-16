package tn.esprit.innoxpert.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.Entity.Bloc;
import tn.esprit.innoxpert.Entity.Chambre;
import tn.esprit.innoxpert.Repository.BlocRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BlocService implements BlocServiceInterface {

    private final BlocRepository blocRepository;

    public void addBlocWithChambres(Bloc bloc) {
        // Set the bidirectional relationship for each Chambre
        if (bloc.getChambres() != null) {
            for (Chambre chambre : bloc.getChambres()) {
                chambre.setBloc(bloc); // Link each Chambre to the Bloc
            }
        }

        blocRepository.save(bloc); // Saves both Bloc and Chambres
    }

    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }

    public Bloc getBlocById(Long id) {
        return blocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloc not found with ID: " + id));
    }

    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    public Bloc updateBloc(Long id, Bloc blocDetails) {
        Bloc bloc = getBlocById(id);
        bloc.setNomBloc(blocDetails.getNomBloc());
        bloc.setNombreEtages(blocDetails.getNombreEtages());
        return blocRepository.save(bloc);
    }

    public void deleteBloc(Long id) {
        blocRepository.deleteById(id);
    }
    @Override
    public List<Bloc> getBlocsDisponibles() {
        List<Bloc> allBlocs = blocRepository.findAll();
        return allBlocs.stream()
                .filter(bloc -> bloc.getChambres() != null &&
                        bloc.getChambres().stream().anyMatch(Chambre::isDisponible))
                .toList();
    }
    public Map<String, Object> getTauxOccupation(Long blocId) {
        Bloc bloc = blocRepository.findById(blocId)
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé"));

        List<Chambre> chambres = bloc.getChambres();
        if (chambres == null || chambres.isEmpty()) {
            return Map.of(
                    "blocId", bloc.getIdBloc(),
                    "nomBloc", bloc.getNomBloc(),
                    "tauxOccupation", "0%"
            );
        }

        long total = chambres.size();
        long occupees = chambres.stream().filter(ch -> !ch.isDisponible()).count();
        double taux = (double) occupees / total * 100;

        return Map.of(
                "blocId", bloc.getIdBloc(),
                "nomBloc", bloc.getNomBloc(),
                "tauxOccupation", String.format("%.2f%%", taux)
        );

    }


}
