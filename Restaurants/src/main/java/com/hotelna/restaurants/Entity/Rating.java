package com.hotelna.restaurants.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private int id;
    @NotNull(message = "Rate is required")
    @Enumerated(EnumType.STRING)
    private Rate rate;
    @NotBlank(message = "Comment is required")
    @Size(min = 4, message = "Comment must be at least 4 characters")
    private String comment;

    private String mail;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Rating() {
    }

    public Rating(int id, Rate rate, String comment, String mail, Restaurant restaurant) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
        this.mail = mail;
        this.restaurant = restaurant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "restaurantId=" + restaurant.getId() +
                ", comment='" + comment + '\'' +
                ", score=" + rate +
                '}';
    }
}
