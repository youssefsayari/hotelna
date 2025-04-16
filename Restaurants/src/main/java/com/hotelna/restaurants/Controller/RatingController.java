package com.hotelna.restaurants.Controller;

import com.hotelna.restaurants.DTO.RatingDTO;
import com.hotelna.restaurants.Entity.Rating;
import com.hotelna.restaurants.Service.RatingService;
import com.hotelna.restaurants.Service.Table_ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/rating")
public class RatingController {
    @Autowired
    RatingService ratingService;

    @PostMapping("/add-rating")
    public ResponseEntity<Rating> addRating(@RequestBody RatingDTO ratingDTO) {
        Rating savedRating = ratingService.createRating(ratingDTO);
        return ResponseEntity.ok(savedRating);
    }

    @GetMapping("/get-all-ratings")
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        if (ratings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/get-ratings-by-restaurant/{restaurant-id}")
    public ResponseEntity<List<Rating>> getRatingsByRestaurantId(@PathVariable("restaurant-id") int restaurantId) {
        List<Rating> ratings = ratingService.getRatingsByRestaurantId(restaurantId);
        if (ratings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/retrieve-rating/{rating-id}")
    public ResponseEntity<Rating> getRating(@PathVariable("rating-id") int idRating) {
        try {
            Rating rating = ratingService.getRatingById(idRating);
            return ResponseEntity.ok(rating);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
