package com.hotelna.restaurants.Service;

import com.hotelna.restaurants.Entity.Rating;
import com.hotelna.restaurants.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating createRating(Rating rating) {
        try {
            if (rating == null) {
                throw new IllegalArgumentException("Rating object is null");
            }
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
