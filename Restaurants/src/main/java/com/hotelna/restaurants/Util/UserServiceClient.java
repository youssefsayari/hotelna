package com.hotelna.restaurants.Util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.hotelna.restaurants.DTO.UserResponseDTO;

@FeignClient(name = "user")
public interface UserServiceClient {

    @GetMapping("/user/getUserById/{idUser}")
    UserResponseDTO getUserById(@PathVariable("idUser") Long userId); // Spécifier le nom du path variable

}
