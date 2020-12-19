package com.khangse616.serverecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khangse616.serverecommerce.models.Image;

import java.math.BigDecimal;

public class ProductItemDTO {
    private int id;
    private String name;
    private int adminId;
    private boolean event;
    private String category;
    private boolean freeShip;
    private boolean promotion;
    private BigDecimal price;
    private int promotionPercent;
    private BigDecimal finalPrice;
    private int orderCount;
    private String imgUrl;
    private int shopId;
    private String shopName;
    private String shopWarehouseCity;
    private float percentStar;
    private int countRating;

    public ProductItemDTO() {
    }

    @JsonProperty("shop_id")
    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @JsonProperty("shop_name")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @JsonProperty("shop_warehouse_city")
    public String getShopWarehouseCity() {
        return shopWarehouseCity;
    }

    public void setShopWarehouseCity(String shopWarehouseCity) {
        this.shopWarehouseCity = shopWarehouseCity;
    }

    @JsonProperty("percent_star")
    public float getPercentStar() {
        return percentStar;
    }

    public void setPercentStar(float percentStar) {
        this.percentStar = percentStar;
    }

    @JsonProperty("count_rating")
    public int getCountRating() {
        return countRating;
    }

    public void setCountRating(int countRating) {
        this.countRating = countRating;
    }

    @JsonProperty("img_url")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @JsonProperty("order_count")
    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    @JsonProperty("product_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @JsonProperty("is_event")
    public boolean isEvent() {
        return event;
    }

    public void setEvent(boolean event) {
        this.event = event;
    }

    @JsonProperty("category_id")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("free_shipping")
    public boolean isFreeShip() {
        return freeShip;
    }

    public void setFreeShip(boolean freeShip) {
        this.freeShip = freeShip;
    }

    @JsonProperty("is_promotion")
    public boolean isPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonProperty("promotion_percent")
    public int getPromotionPercent() {
        return promotionPercent;
    }

    public void setPromotionPercent(int promotionPercent) {
        this.promotionPercent = promotionPercent;
    }

    @JsonProperty("final_price")
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
