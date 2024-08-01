package com.example.shopstock;

import java.io.Serializable;

public class StocksData implements Serializable {
    private int id;
    private String title;
    private int quantity;
    private double price;

    public StocksData(int id, String title, int quantity, double price){
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;

    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(){
        this.title = title;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(){
        this.quantity = quantity;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice() {
        this.price = price;
    }
}
