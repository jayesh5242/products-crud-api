package com.example.Products_CRUD_API.Service;

import com.example.Products_CRUD_API.dto.ProductRequestDTO;
import com.example.Products_CRUD_API.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO getProductById(Long id);

    Page<ProductResponseDTO> getAllProducts(Pageable pageable);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);

    void deleteProduct(Long id);
}