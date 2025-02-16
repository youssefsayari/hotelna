package com.hotelna.restaurants.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String statut;
    private String typeRestaurant;
    private String menu;

    public Restaurant() {
    }

    public Restaurant(int id, String name, String statut, String typeRestaurant, String menu) {
        this.id = id;
        this.name = name;
        this.statut = statut;
        this.typeRestaurant = typeRestaurant;
        this.menu = menu;
    }
    public Restaurant( String name, String statut, String typeRestaurant, String menu) {
        this.name = name;
        this.statut = statut;
        this.typeRestaurant = typeRestaurant;
        this.menu = menu;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTypeRestaurant() {
        return typeRestaurant;
    }

    public void setTypeRestaurant(String typeRestaurant) {
        this.typeRestaurant = typeRestaurant;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }


}
