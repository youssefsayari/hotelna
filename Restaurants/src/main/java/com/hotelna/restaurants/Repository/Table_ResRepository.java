package com.hotelna.restaurants.Repository;

import com.hotelna.restaurants.Entity.Table_Res;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Table_ResRepository extends JpaRepository<Table_Res, Integer> {
    List<Table_Res> findByRestaurantId(int restaurantId);
    void deleteAllByRestaurantId(int restaurantId);

}
