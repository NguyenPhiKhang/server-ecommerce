package com.khangse616.serverecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RatingProductDTO {
    private int totalCount;
    private List<RatingDTO> data;

    public RatingProductDTO() {
    }

    @JsonProperty("total_count")
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<RatingDTO> getData() {
        return data;
    }

    public void setData(List<RatingDTO> data) {
        this.data = data;
    }
}
