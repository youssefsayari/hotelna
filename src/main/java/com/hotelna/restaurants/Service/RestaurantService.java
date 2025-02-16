package com.hotelna.restaurants.Service;

import com.hotelna.restaurants.Entity.Restaurant;
import com.hotelna.restaurants.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService implements IRestaurantService {
    @Autowired
    private RestaurantRepository repository;

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        if (repository.existsById(restaurant.getId())) {
            return repository.save(restaurant);
        }
        throw new RuntimeException("Restaurant not found");
    }

    @Override
    public void deleteRestaurant(int id) {
        repository.deleteById(id);
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return repository.findAll();
    }
}
