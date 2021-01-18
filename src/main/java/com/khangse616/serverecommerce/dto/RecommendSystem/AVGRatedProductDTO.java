package com.khangse616.serverecommerce.dto.RecommendSystem;

import javax.persistence.Column;
import java.util.Objects;

public class AVGRatedProductDTO {
//    int getProductId();
//    float getAvgRated();
//    @Column("product_id")
    private int productId;
//    @Column("avg_rated")
    private double avgRated;

    public AVGRatedProductDTO() {
    }

    public AVGRatedProductDTO(int productId, double avgRated) {
        this.productId = productId;
        this.avgRated = avgRated;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getAvgRated() {
        return avgRated;
    }

    public void setAvgRated(double avgRated) {
        this.avgRated = avgRated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AVGRatedProductDTO)) return false;
        AVGRatedProductDTO that = (AVGRatedProductDTO) o;
        return getProductId() == that.getProductId() &&
                Double.compare(that.getAvgRated(), getAvgRated()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getAvgRated());
    }
}
