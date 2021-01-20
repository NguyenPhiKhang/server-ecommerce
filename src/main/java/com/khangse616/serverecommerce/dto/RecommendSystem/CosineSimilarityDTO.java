package com.khangse616.serverecommerce.dto.RecommendSystem;

import java.util.Objects;

public class CosineSimilarityDTO {
    private int row;
    private int column;
    private double similarity;

    public CosineSimilarityDTO() {
    }

    public CosineSimilarityDTO(int row, int column, double similarity) {
        this.row = row;
        this.column = column;
        this.similarity = similarity;
    }
    public CosineSimilarityDTO(int row, int column) {
        this.row = row;
        this.column = column;
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

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    @Override
    public String toString() {
        return "CosineSimilarityDTO{" +
                "row=" + row +
                ", column=" + column +
                ", similarity=" + similarity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CosineSimilarityDTO)) return false;
        CosineSimilarityDTO that = (CosineSimilarityDTO) o;
        return getRow() == that.getRow() &&
                getColumn() == that.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn(), getSimilarity());
    }
}
