package tn.esprit.innoxpert.Util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.innoxpert.Entity.Spa;

import java.util.List;

@FeignClient(name = "spa-client", url = "http://localhost:5000")
public interface SpaClient {

    @GetMapping("/spa/getAllSpas")
    List<Spa> getAllSpas();

    @GetMapping("/spa/getSpaById/{id}")
    Spa getSpaById(@PathVariable String id);
}
