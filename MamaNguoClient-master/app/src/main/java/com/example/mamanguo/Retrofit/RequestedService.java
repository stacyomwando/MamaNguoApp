package com.example.mamanguo.Retrofit;

public class RequestedService {

    private int userId;
    private int requesteeId;
    private String description;
    private String quantity;
    private String cost;
    private double longitude;
    private double latitude;
    private String location;
    private int totalCost;

    private String message;
    private String status;

    public int getUserId() {
        return userId;
    }

    public int getRequesteeId() {
        return requesteeId;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCost() {
        return cost;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getLocation() {
        return location;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public RequestedService(int userId, int requesteeId, String description, String quantity, String cost, double longitude, double latitude, String location, int totalCost) {
        this.userId = userId;
        this.requesteeId = requesteeId;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
        this.totalCost = totalCost;
    }
}
