package com.hotelna.restaurants.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Table_Res {
    @Id
    @GeneratedValue
    private int id;

    @Min(value = 1, message = "Number of seats must be at least 1")
    private int nbPlaces;

    @NotNull(message = "Availability must not be null")
    private Boolean disponibilite;

    @NotNull(message = "UserId must not be null")
    private int UserId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Table_Res() {
    }

    public Table_Res(int id, int nbPlaces, Boolean disponibilite, int userId, Restaurant restaurant) {
        this.id = id;
        this.nbPlaces = nbPlaces;
        this.disponibilite = disponibilite;
        UserId = userId;
        this.restaurant = restaurant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public Boolean getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
