package com.khangse616.serverecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShopDTO {
    private int id;
    private int customerIdOfShop;
    private String shopName;
    private String shopLogo;
    private float goodReviewPercent;
    private int score;
    private String phoneNumber;
    private String warehouseCity;
    private boolean certified;

    public ShopDTO() {
    }

    @JsonProperty("shop_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("customer_id_of_shop")
    public int getCustomerIdOfShop() {
        return customerIdOfShop;
    }

    public void setCustomerIdOfShop(int customerIdOfShop) {
        this.customerIdOfShop = customerIdOfShop;
    }

    @JsonProperty("shop_name")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @JsonProperty("shop_logo")
    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    @JsonProperty("good_review_percent")
    public float getGoodReviewPercent() {
        return goodReviewPercent;
    }

    public void setGoodReviewPercent(float goodReviewPercent) {
        this.goodReviewPercent = goodReviewPercent;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("shop_warehouse_city")
    public String getWarehouseCity() {
        return warehouseCity;
    }

    public void setWarehouseCity(String warehouseCity) {
        this.warehouseCity = warehouseCity;
    }

    @JsonProperty("is_certified")
    public boolean isCertified() {
        return certified;
    }

    public void setCertified(boolean certified) {
        this.certified = certified;
    }
}
