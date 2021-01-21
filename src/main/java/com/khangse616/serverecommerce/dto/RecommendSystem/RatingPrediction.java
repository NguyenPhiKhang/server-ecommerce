package com.khangse616.serverecommerce.dto.RecommendSystem;

public class RatingPrediction {
    private int productId;
    private double prediction;

    public RatingPrediction() {
    }

    public RatingPrediction(int productId, double prediction) {
        this.productId = productId;
        this.prediction = prediction;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrediction() {
        return prediction;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }
}
