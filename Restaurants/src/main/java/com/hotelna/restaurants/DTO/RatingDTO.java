package com.hotelna.restaurants.DTO;

import com.hotelna.restaurants.Entity.Rate;

public class RatingDTO {
    private int restaurantId;
    private Rate score;
    private String comment;
    private String mail;



    public Rate getScore() {
        return score;
    }

    public void setScore(Rate score) {
        this.score = score;
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


    public RatingDTO() {
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public RatingDTO(int restaurantId, Rate score, String comment, String mail) {
        this.restaurantId = restaurantId;
        this.score = score;
        this.comment = comment;
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "RatingDTO{" +
                "restaurantId=" + restaurantId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
