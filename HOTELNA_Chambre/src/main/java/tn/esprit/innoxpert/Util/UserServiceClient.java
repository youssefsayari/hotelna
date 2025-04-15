package tn.esprit.innoxpert.Util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.innoxpert.Dto.UserResponseDTO;
@FeignClient(name = "user")
public interface UserServiceClient {

    @GetMapping("/user/getUserById/{idUser}")
    UserResponseDTO getUserById(@PathVariable("idUser") Long userId); // Spécifier le nom du path variable
}
