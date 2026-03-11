package com.example.Products_CRUD_API.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemResponseDTO {

    private Long id;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("product_id")
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
