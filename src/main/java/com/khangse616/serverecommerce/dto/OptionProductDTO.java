package com.khangse616.serverecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OptionProductDTO {
    private int id;
    private String value;

    public OptionProductDTO(int id, String value) {
        this.id = id;
        this.value = value;
    }

    @JsonProperty("option_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
