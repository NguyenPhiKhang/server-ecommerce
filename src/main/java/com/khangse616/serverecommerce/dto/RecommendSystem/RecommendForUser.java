package com.khangse616.serverecommerce.dto.RecommendSystem;

public class RecommendForUser {
    private int userId;
    private String listProducts;

    public RecommendForUser() {
    }

    public RecommendForUser(int userId, String listProducts) {
        this.userId = userId;
        this.listProducts = listProducts;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getListProducts() {
        return listProducts;
    }

    public void setListProducts(String listProducts) {
        this.listProducts = listProducts;
    }

    @Override
    public String toString() {
        return "RecommendForUser{" +
                "userId=" + userId +
                ", listProducts='" + listProducts + '\'' +
                '}';
    }
}
