package com.hotelna.restaurants.Controller;

import com.hotelna.restaurants.Entity.Restaurant;
import com.hotelna.restaurants.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/retaurants")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/add-restaurant")
    public Restaurant addRestaurant(@RequestBody Restaurant r)
    {
        return restaurantService.createRestaurant(r);
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
    @GetMapping("/retrieve-all-restaurants")
    public List<Restaurant> getAllRestaurants()
    {
        return restaurantService.getAllRestaurants();
    }
}
