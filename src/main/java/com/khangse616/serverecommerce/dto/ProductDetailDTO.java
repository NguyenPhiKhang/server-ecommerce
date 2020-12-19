package com.khangse616.serverecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.khangse616.serverecommerce.models.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductDetailDTO {
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
    private String description;
    private String shortDescription;
    private int weight;
    private int quantity;
    private boolean active;
    private boolean shopFreeShipping;
    private int orderCount;
    private boolean stockStatus;
    private String sku;
    private String skuUser;
    private RatingStar ratingStar;
    private List<String> images;
    private List<AttributeProductDTO> attribute;
    private CommentProductDTO comments;
    private RatingProductDTO ratings;
    private CategoriesDTO categories;
    private ShopDTO shop;

    public ProductDetailDTO() {
    }

    @JsonProperty("shop_info")
    public ShopDTO getShop() {
        return shop;
    }

    public void setShop(ShopDTO shop) {
        this.shop = shop;
    }

    public CategoriesDTO getCategories() {
        return categories;
    }

    public void setCategories(CategoriesDTO categories) {
        this.categories = categories;
    }

    public RatingProductDTO getRatings() {
        return ratings;
    }

    public void setRatings(RatingProductDTO ratings) {
        this.ratings = ratings;
    }

    public CommentProductDTO getComments() {
        return comments;
    }

    public void setComments(CommentProductDTO comments) {
        this.comments = comments;
    }

    public List<AttributeProductDTO> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<AttributeProductDTO> attribute) {
        this.attribute = attribute;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @JsonProperty("rating_info")
    @JsonIgnoreProperties(value = {
            "id",
            "product"
    })
    public RatingStar getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(RatingStar ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @JsonProperty("sku_user")
    public String getSkuUser() {
        return skuUser;
    }

    public void setSkuUser(String skuUser) {
        this.skuUser = skuUser;
    }

    @JsonProperty("stock_status")
    public boolean isStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(boolean stockStatus) {
        this.stockStatus = stockStatus;
    }

    @JsonProperty("shop_free_shipping")
    public boolean isShopFreeShipping() {
        return shopFreeShipping;
    }

    public void setShopFreeShipping(boolean shopFreeShipping) {
        this.shopFreeShipping = shopFreeShipping;
    }

    @JsonProperty("order_count")
    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("is_active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("short_description")
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @JsonProperty("final_price")
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    @JsonProperty("category_id")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("promotion_percent")
    public int getPromotionPercent() {
        return promotionPercent;
    }

    public void setPromotionPercent(int promotionPercent) {
        this.promotionPercent = promotionPercent;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonProperty("is_promotion")
    public boolean isPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    @JsonProperty("is_event")
    public boolean isEvent() {
        return event;
    }

    public void setEvent(boolean event) {
        this.event = event;
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

    @JsonProperty("admin_id")
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @JsonProperty("is_free_ship")
    public boolean isFreeShip() {
        return freeShip;
    }

    public void setFreeShip(boolean freeShip) {
        this.freeShip = freeShip;
    }
}
