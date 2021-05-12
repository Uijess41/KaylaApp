package com.kayla.Model;

public class NewArrivalModel {

    private String name;
    private String price;
    private int imaage;

    public NewArrivalModel(String name, String price, int imaage) {
        this.name = name;
        this.price = price;
        this.imaage = imaage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImaage() {
        return imaage;
    }

    public void setImaage(int imaage) {
        this.imaage = imaage;
    }
}
