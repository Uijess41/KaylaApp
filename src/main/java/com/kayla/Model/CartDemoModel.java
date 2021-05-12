package com.kayla.Model;

public class CartDemoModel {
    private String productname;
    private String product_title;
    private int price;
    private int image;

    public CartDemoModel(String productname, String product_title, int price,int image) {
        this.productname = productname;
        this.product_title = product_title;
        this.price = price;
        this.image=image;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
