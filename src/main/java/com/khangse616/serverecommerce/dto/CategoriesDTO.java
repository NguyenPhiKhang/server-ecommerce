package com.khangse616.serverecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoriesDTO {

    private int categoryLevel1Id;
    private String categoryLevel1Name;
    private String categoryLevel1Path;
    private int categoryLevel2Id;
    private String categoryLevel2Name;
    private String categoryLevel2Path;
    private int categoryLevel3Id;
    private String categoryLevel3Name;
    private String categoryLevel3Path;

    public CategoriesDTO() {
    }

    @JsonProperty("category_level1_id")
    public int getCategoryLevel1Id() {
        return categoryLevel1Id;
    }

    public void setCategoryLevel1Id(int categoryLevel1Id) {
        this.categoryLevel1Id = categoryLevel1Id;
    }

    @JsonProperty("category_level1_name")
    public String getCategoryLevel1Name() {
        return categoryLevel1Name;
    }

    public void setCategoryLevel1Name(String categoryLevel1Name) {
        this.categoryLevel1Name = categoryLevel1Name;
    }

    @JsonProperty("category_level1_path")
    public String getCategoryLevel1Path() {
        return categoryLevel1Path;
    }

    public void setCategoryLevel1Path(String categoryLevel1Path) {
        this.categoryLevel1Path = categoryLevel1Path;
    }

    @JsonProperty("category_level2_id")
    public int getCategoryLevel2Id() {
        return categoryLevel2Id;
    }

    public void setCategoryLevel2Id(int categoryLevel2Id) {
        this.categoryLevel2Id = categoryLevel2Id;
    }

    @JsonProperty("category_level2_name")
    public String getCategoryLevel2Name() {
        return categoryLevel2Name;
    }

    public void setCategoryLevel2Name(String categoryLevel2Name) {
        this.categoryLevel2Name = categoryLevel2Name;
    }

    @JsonProperty("category_level2_path")
    public String getCategoryLevel2Path() {
        return categoryLevel2Path;
    }

    public void setCategoryLevel2Path(String categoryLevel2Path) {
        this.categoryLevel2Path = categoryLevel2Path;
    }

    @JsonProperty("category_level3_id")
    public int getCategoryLevel3Id() {
        return categoryLevel3Id;
    }

    public void setCategoryLevel3Id(int categoryLevel3Id) {
        this.categoryLevel3Id = categoryLevel3Id;
    }

    @JsonProperty("category_level3_name")
    public String getCategoryLevel3Name() {
        return categoryLevel3Name;
    }

    public void setCategoryLevel3Name(String categoryLevel3Name) {
        this.categoryLevel3Name = categoryLevel3Name;
    }

    @JsonProperty("category_level3_path")
    public String getCategoryLevel3Path() {
        return categoryLevel3Path;
    }

    public void setCategoryLevel3Path(String categoryLevel3Path) {
        this.categoryLevel3Path = categoryLevel3Path;
    }
}
