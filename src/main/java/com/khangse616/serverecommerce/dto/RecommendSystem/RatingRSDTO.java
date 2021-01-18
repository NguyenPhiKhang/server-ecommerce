package com.khangse616.serverecommerce.dto.RecommendSystem;

import java.util.Objects;

public class RatingRSDTO {
    private int userId;
    private int productId;
    private double value;

    public RatingRSDTO() {
    }

    public RatingRSDTO(int userId, int productId, double value) {
        this.userId = userId;
        this.productId = productId;
        this.value = value;
    }

    public RatingRSDTO(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RatingRSDTO{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingRSDTO)) return false;
        RatingRSDTO that = (RatingRSDTO) o;
        return getUserId() == that.getUserId() &&
                getProductId() == that.getProductId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getProductId(), getValue());
    }
}
