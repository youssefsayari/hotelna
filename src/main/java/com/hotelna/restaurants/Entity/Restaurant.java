package com.hotelna.restaurants.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Time;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String descritpion;
    private String statut;
    private String typeRestaurant;
    private Time openTime;
    private Time closeTime;

    public Restaurant() {
    }

    public Restaurant(String name, String descritpion, String statut, String typeRestaurant, Time openTime, Time closeTime) {
        this.name = name;
        this.descritpion = descritpion;
        this.statut = statut;
        this.typeRestaurant = typeRestaurant;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public Restaurant(int id, String name, String descritpion, String statut, String typeRestaurant, Time openTime, Time closeTime) {
        this.id = id;
        this.name = name;
        this.descritpion = descritpion;
        this.statut = statut;
        this.typeRestaurant = typeRestaurant;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
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

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }
}
