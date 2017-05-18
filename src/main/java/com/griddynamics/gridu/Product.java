package com.griddynamics.gridu;

public class Product {
    final String name;
    final float price;

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public static Product Product(String name, float price) {
        return new Product(name, price);
    }
}
