package tn.esprit.innoxpert.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.Entity.Bloc;
import tn.esprit.innoxpert.Repository.BlocRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BlocService implements BlocServiceInterface {

    private final BlocRepository blocRepository;





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



}
