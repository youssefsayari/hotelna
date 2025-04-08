package com.hotelna.restaurants.Controller;

import com.hotelna.restaurants.Entity.Restaurant;
import com.hotelna.restaurants.Entity.Status;
import com.hotelna.restaurants.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/add-restaurant")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant r) {
        r.setStatut(Status.CLOSED);
        Restaurant saved = restaurantService.createRestaurant(r);
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/retrieve-all-restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(restaurants);
    }


    @PutMapping("/update-restaurant")
    public Restaurant updateRestaurant(@RequestBody Restaurant r)
    {
        return restaurantService.updateRestaurant(r);
    }
    @DeleteMapping("/remove-restaurant/{resto-id}")
    public void deleteRestaurant(@PathVariable("resto-id") int idResto)
    {
        restaurantService.deleteRestaurant(idResto);
    }
    @GetMapping("/retrieve-retaurant/{resto-id}")
    public Restaurant getRestaurant(@PathVariable("resto-id")int idResto)
    {
        return restaurantService.getRestaurantById(idResto);
    }

}
