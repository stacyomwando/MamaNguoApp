package com.example.mamanguo.chooseServices.helperClasses;

import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Order {
    //Order calculation
    public static int billTotal;
    public static String[] orderItems;
    public static ArrayList<Integer> orderQuantity;
    public static ArrayList<Integer> unitPrice;
    public static ArrayList<Integer> orderSubtotal;
    //Data structures to hold the activity data
    public static Map<String, Integer> serviceCount = new HashMap<>();
    public static Map<String, Integer> serviceTotal = new HashMap<>();
    public static Map<String, Integer> serviceUnitPrice= new HashMap<>();

    public static int getMamanguoId() {
        return mamanguoId;
    }

    public static int mamanguoId;

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        Order.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        Order.longitude = longitude;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Order.location = location;
    }

    public static int getRequesteeId() {
        return requesteeId;
    }

    public static void setRequesteeId(int requesteeId) {
        Order.requesteeId = requesteeId;
    }

    //Location
    private static double latitude;
    private static double longitude;
    private static String location;

    //Mamanguo
    private static int requesteeId;

    public static int getBillTotal() {
        return billTotal;
    }

    public static void setBillTotal(int billTotal) {
        Order.billTotal = billTotal;
    }


    public static String[] mapKeyToArray(Map<String, Integer> map) {
        int size = map.size();
        String[] keys = map.keySet().toArray(new String[size]);
        return keys;
    }

    public static ArrayList<Integer> mapValueToArray(Map<String, Integer> map) {
        ArrayList<Integer> arrayList = new ArrayList<>(map.values());
        return arrayList;
    }

    public static String createStringFromList(ArrayList<Integer>list)
    {
        StringBuilder newString = new StringBuilder();

        for(Integer i : list) {
            newString.append(i);
            newString.append(",");
        }
        return newString.toString();
    }

    public static String createStringFromArray(String[] array) {
        StringBuilder newString = new StringBuilder();

        for(String str : array) {
            newString.append(str);
            newString.append(",");
        }
        return newString.toString();
    }
}
