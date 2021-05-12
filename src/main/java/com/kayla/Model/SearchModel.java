package com.kayla.Model;

public class SearchModel {
    private int imsge;
    private String name;
    private int price;

    public SearchModel(int imsge, String name, int price) {
        this.imsge = imsge;
        this.name = name;
        this.price = price;
    }

    public int getImsge() {
        return imsge;
    }

    public void setImsge(int imsge) {
        this.imsge = imsge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
