package com.hotelna.restaurants.Service;

import com.hotelna.restaurants.DTO.RatingDTO;
import com.hotelna.restaurants.Entity.Rating;
import com.hotelna.restaurants.Entity.Restaurant;
import com.hotelna.restaurants.Repository.RatingRepository;
import com.hotelna.restaurants.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Rating createRating(RatingDTO ratingDTO) {
        try {
            if (ratingDTO == null) {
                throw new IllegalArgumentException("RatingDTO object is null");
            }

            Restaurant restaurant = restaurantRepository.findById(ratingDTO.getRestaurantId())
                    .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
            Rating rating = new Rating();
            rating.setRestaurant(restaurant);
            rating.setRate(ratingDTO.getScore());
            rating.setComment(ratingDTO.getComment());
            rating.setMail(ratingDTO.getMail());

            return ratingRepository.save(rating);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error while creating rating: " + e.getMessage());
            throw new RuntimeException("Failed to create rating", e);
        }
    }


    @Override
    public Rating getRatingById(int id) {
        try {
            if (!ratingRepository.existsById(id)) {
                throw new RuntimeException("Rating not found with ID: " + id);
            }
            return ratingRepository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve rating", e);
        }
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingsByRestaurantId(int restaurantId) {
        try {
            return ratingRepository.findByRestaurantId(restaurantId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve ratings for restaurant", e);
        }
    }
}
