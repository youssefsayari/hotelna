package com.hotelna.restaurants.Entity;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalTime;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status statut;
    private String typeRestaurant;
    private LocalTime openTime;
    private LocalTime closeTime;

    public Restaurant() {
    }

    public Restaurant(int id, String name, String description, Status statut, String typeRestaurant, LocalTime openTime, LocalTime closeTime) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatut() {
        return statut;
    }

    public void setStatut(Status statut) {
        this.statut = statut;
    }

    public String getTypeRestaurant() {
        return typeRestaurant;
    }

    public void setTypeRestaurant(String typeRestaurant) {
        this.typeRestaurant = typeRestaurant;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }
}
