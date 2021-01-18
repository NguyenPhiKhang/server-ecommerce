package com.khangse616.serverecommerce.dto;

public class RatingRSDTO {
    private int userId;
    private int productId;
    private int star;

    public RatingRSDTO(int userId, int productId, int star) {
        this.userId = userId;
        this.productId = productId;
        this.star = star;
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

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
