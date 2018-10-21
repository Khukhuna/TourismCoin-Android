package com.toursimcoin.tourismcoin_android.model;

public class Sale {
    private String title;
    private String description;
    private String image_url;
    private int price;

    public Sale(String title, String description, String image_url, int price){
        this.title = title;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
