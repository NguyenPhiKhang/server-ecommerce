package com.khangse616.serverecommerce.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cosine_similarity")
@IdClass(CosineSimilarityId.class)
public class CosineSimilarity implements Serializable {
    @Id
    @Column(name = "row_product_id")
    private int row;
    @Id
    @Column(name = "column_product_id")
    private int column;

    @Column(name = "similarity")
    private double cosSimilarity;

    public CosineSimilarity() {
    }

    public CosineSimilarity(int row, int column, double cosSimilarity) {
        this.row = row;
        this.column = column;
        this.cosSimilarity = cosSimilarity;
    }

    public CosineSimilarity(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public double getCosSimilarity() {
        return cosSimilarity;
    }

    public void setCosSimilarity(double cosSimilarity) {
        this.cosSimilarity = cosSimilarity;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    @Override
    public String toString() {
        return "CosineSimilarityDTO{" +
                "row=" + row +
                ", column=" + column +
                ", similarity=" + cosSimilarity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CosineSimilarity)) return false;
        CosineSimilarity that = (CosineSimilarity) o;
        return getRow() == that.getRow() &&
                getColumn() == that.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn(), getCosSimilarity());
    }
}
