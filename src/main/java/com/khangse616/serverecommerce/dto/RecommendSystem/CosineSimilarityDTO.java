package com.khangse616.serverecommerce.dto.RecommendSystem;

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
}
