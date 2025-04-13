package tn.esprit.innoxpert.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.Entity.Spa;
import tn.esprit.innoxpert.Util.SpaClient;

import java.util.List;

@Service
public class SpaService {

    @Autowired
    private SpaClient spaClient;

    public List<Spa> fetchAllSpas() {
        return spaClient.getAllSpas();
    }

    public Spa fetchSpaById(String id) {
        return spaClient.getSpaById(id);
    }
}
