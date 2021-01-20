package com.khangse616.serverecommerce.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recommend_rating")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RecommendRating {

    @Id
    @Column(name = "user_id")
    private int userId;
    @Column(name = "products")
    private String products;

    public RecommendRating() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
}
