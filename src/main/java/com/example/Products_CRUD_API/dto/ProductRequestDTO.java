package com.example.Products_CRUD_API.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class ProductRequestDTO {

    @JsonProperty("product_name")
    @NotBlank(message = "product_name is required")
    private String productName;

    @JsonProperty("created_by")
    @NotBlank(message = "created_by is required")
    private String createdBy;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
