package com.hotelna.restaurants.Repository;

import com.hotelna.restaurants.Entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByRestaurantId(int restaurantId);
}
