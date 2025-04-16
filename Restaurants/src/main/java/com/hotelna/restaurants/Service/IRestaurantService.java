package com.hotelna.restaurants.Service;

import com.hotelna.restaurants.Entity.Restaurant;

import java.util.List;

public interface IRestaurantService {
    public Restaurant createRestaurant(Restaurant restaurant);
    public Restaurant updateRestaurant(Restaurant restaurant);
    public void deleteRestaurant(int id);
    public Restaurant getRestaurantById(int id);
    public List<Restaurant> getAllRestaurants();
}
