package com.khangse616.serverecommerce.dto;

import java.util.List;

public class CommentProductDTO {
    private int totalCount;
    private List<CommentDTO> data;

    public CommentProductDTO() {
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<CommentDTO> getData() {
        return data;
    }

    public void setData(List<CommentDTO> data) {
        this.data = data;
    }
}
