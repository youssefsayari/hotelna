package com.hotelna.restaurants.Repository;

import com.hotelna.restaurants.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
