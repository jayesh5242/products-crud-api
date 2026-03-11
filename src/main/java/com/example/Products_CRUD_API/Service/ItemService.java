package com.example.Products_CRUD_API.Service;

import com.example.Products_CRUD_API.dto.ItemRequestDTO;
import com.example.Products_CRUD_API.dto.ItemResponseDTO;

import java.util.List;

public interface ItemService {

    ItemResponseDTO addItemToProduct(Long productId, ItemRequestDTO dto);

    List<ItemResponseDTO> getItemsByProductId(Long productId);
}