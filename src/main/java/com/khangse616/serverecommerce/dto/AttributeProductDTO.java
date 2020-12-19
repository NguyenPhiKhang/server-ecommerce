package com.khangse616.serverecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AttributeProductDTO {
    private int id;
    private String name;
    private List<OptionProductDTO> value;

    public AttributeProductDTO(int id, String name, List<OptionProductDTO> value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    @JsonProperty("attribute_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OptionProductDTO> getValue() {
        return value;
    }

    public void setValue(List<OptionProductDTO> value) {
        this.value = value;
    }
}
