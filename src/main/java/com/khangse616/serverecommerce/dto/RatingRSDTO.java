package com.khangse616.serverecommerce.dto;

public class RatingRSDTO {
    private int userId;
    private int productId;
    private int value;

    public RatingRSDTO() {
    }

    public RatingRSDTO(int userId, int productId, int value) {
        this.userId = userId;
        this.productId = productId;
        this.value = value;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
