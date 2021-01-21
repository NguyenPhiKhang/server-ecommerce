package com.khangse616.serverecommerce.models;

import java.io.Serializable;

public class CosineSimilarityId implements Serializable {
    private int row;
    private int column;

    public CosineSimilarityId() {
    }

    public CosineSimilarityId(int row, int column) {
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
}
