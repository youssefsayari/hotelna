package com.hotelna.restaurants.DTO;


public class UserResponseDTO {
    private int idUser;
    private String firstName;
    private String lastName;
    private String email;

    public UserResponseDTO(int idUser, String firstName, String lastName, String email) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public UserResponseDTO() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
// ... autres champs (sans OTP/password)
}
