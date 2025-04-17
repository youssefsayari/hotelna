package tn.esprit.innoxpert.Controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.innoxpert.Entity.Bloc;
import tn.esprit.innoxpert.Service.BlocService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blocs")
@AllArgsConstructor
public class BlocController {

    private final BlocService blocService;

    @GetMapping("/get")
    public List<Bloc> getAllBlocs() {
        return blocService.getAllBlocs();
    }

    @GetMapping("/getById/{id}")
    public Bloc getBlocById(@PathVariable Long id) {
        return blocService.getBlocById(id);
    }

    @PostMapping("/add")
    public Bloc addBlocWithChambres(@RequestBody Bloc bloc) {
        return blocService.addBloc(bloc);
    }

    @PutMapping("/update/{id}")
    public Bloc updateBloc(@PathVariable Long id, @RequestBody Bloc bloc) {
        return blocService.updateBloc(id, bloc);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteBloc(@PathVariable Long id) {
        // Perform the deletion logic
        blocService.deleteBloc(id);

        // Return a JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Bloc supprimé avec succès");
        return ResponseEntity.ok(response);
    }


}
