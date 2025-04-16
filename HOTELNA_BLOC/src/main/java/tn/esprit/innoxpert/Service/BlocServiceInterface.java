package tn.esprit.innoxpert.Service;

import tn.esprit.innoxpert.Entity.Bloc;

import java.util.List;
import java.util.Map;

public interface BlocServiceInterface {
    List<Bloc> getAllBlocs();
    Bloc getBlocById(Long id);
    Bloc addBloc(Bloc bloc);
    Bloc updateBloc(Long id, Bloc bloc);
    void deleteBloc(Long id);
    List<Bloc> getBlocsDisponibles();
    Map<String, Object> getTauxOccupation(Long blocId);
}
