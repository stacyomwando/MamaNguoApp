package com.example.mamanguo.Retrofit.Models;

public class MamaNguo {

    private int mamanguoId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private float rating;

    private String status;

    public String getStatus() {
        return status;
    }

    public int getMamanguoId() {
        return mamanguoId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName(){
        return String.format("%s %s", firstName, lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getRating() {
        return rating;
    }
}

