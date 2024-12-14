package com.example.warehousemanager;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private String location;

    public Product(int id, String name, int quantity, String location) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }
}