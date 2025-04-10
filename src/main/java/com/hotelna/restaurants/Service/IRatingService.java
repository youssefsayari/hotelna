package com.hotelna.restaurants.Service;

import com.hotelna.restaurants.Entity.Rating;

import java.util.List;

public interface IRatingService {
    Rating createRating(Rating rating);
    Rating getRatingById(int id);
    List<Rating> getAllRatings();
    List<Rating> getRatingsByRestaurantId(int restaurantId);
}
