package tn.esprit.innoxpert.Util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.innoxpert.Entity.User;

import java.util.List;

@FeignClient(name = "user-client",url="http://localhost:8090")
public interface UserClient {

    @GetMapping("/user/getAllUsers")
    List<User> getAllUsers();


    @GetMapping("/user/getUserById/{idUser}")
    User getUserById(@PathVariable("idUser") Long idUser);





}
