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
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant r) {
        Restaurant updated = restaurantService.updateRestaurant(r);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/remove-restaurant/{resto-id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("resto-id") int idResto) {
        try {
            restaurantService.deleteRestaurant(idResto);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/retrieve-restaurant/{resto-id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable("resto-id") int idResto) {
        try {
            Restaurant restaurant = restaurantService.getRestaurantById(idResto);
            return ResponseEntity.ok(restaurant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
