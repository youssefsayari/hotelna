package com.hotelna.restaurants.Service;

import com.hotelna.restaurants.Entity.Restaurant;
import com.hotelna.restaurants.Repository.RestaurantRepository;
import com.hotelna.restaurants.Repository.Table_ResRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService implements IRestaurantService {
    @Autowired
    private RestaurantRepository repository;
    @Autowired
    private Table_ResRepository tableRepository;

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        try {
            if (restaurant == null) {
                throw new IllegalArgumentException("Restaurant object is null");
            }
            return repository.save(restaurant);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error while creating restaurant: " + e.getMessage());
            throw new RuntimeException("Failed to create restaurant", e);
        }
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        try {
            if (restaurant == null) {
                throw new IllegalArgumentException("Restaurant object is null");
            }
            if (!repository.existsById(restaurant.getId())) {
                throw new RuntimeException("Restaurant not found with ID: " + restaurant.getId());
            }
            return repository.save(restaurant);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update restaurant", e);
        }
    }

    @Override
    @Transactional
    public void deleteRestaurant(int id) {
        try {
            if (!repository.existsById(id)) {
                throw new RuntimeException("Restaurant not found with ID: " + id);
            }
            Restaurant r = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));

            tableRepository.deleteAllByRestaurantId(r.getId());
            tableRepository.flush();
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete restaurant", e);
        }
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        try {
            if (!repository.existsById(id)) {
                throw new RuntimeException("Restaurant not found with ID: " + id);
            }
            return repository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve restaurant", e);
        }
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return repository.findAll();
    }
}
