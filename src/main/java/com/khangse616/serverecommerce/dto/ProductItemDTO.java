package com.khangse616.serverecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khangse616.serverecommerce.models.Image;

import java.math.BigDecimal;

public class ProductItemDTO {
    private int id;
    private String name;
    private int admin_id;
    private boolean event;
    private String category;
    private boolean freeShip;
    private boolean promotion;
    private BigDecimal price;
    private int promotionPercent;
    private BigDecimal finalPrice;
    private int orderCount;
    private Image imgUrl;

    @JsonProperty("img_url")
    public Image getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Image imgUrl) {
        this.imgUrl = imgUrl;
    }

    @JsonProperty("order_count")
    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

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

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
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
