package com.example.mamanguo.Retrofit.Models;

public class Rating {
    private int userId;
    private int mamanguoId;
    private int rating;
    private String comment;

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Rating(int userId, int mamanguoId, int rating, String comment) {
        this.userId = userId;
        this.mamanguoId = mamanguoId;
        this.rating = rating;
        this.comment = comment;
    }
}
