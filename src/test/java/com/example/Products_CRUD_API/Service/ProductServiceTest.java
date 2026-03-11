package com.example.Products_CRUD_API.Service;

import com.example.Products_CRUD_API.dto.ProductRequestDTO;
import com.example.Products_CRUD_API.dto.ProductResponseDTO;
import com.example.Products_CRUD_API.entity.Product;
import com.example.Products_CRUD_API.exception.ResourceNotFoundException;
import com.example.Products_CRUD_API.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setup() {
        product = new Product();
        product.setId(1L);
        product.setProductName("Laptop");
        product.setCreatedBy("admin");
        product.setCreatedOn(LocalDateTime.now());
    }

    // =========================
    // CREATE
    // =========================

    @Test
    void createProduct_success() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setProductName("Laptop");
        dto.setCreatedBy("admin");

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponseDTO response = productService.createProduct(dto);

        assertNotNull(response);
        assertEquals("Laptop", response.getProductName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    // =========================
    // GET BY ID
    // =========================

    @Test
    void getProductById_success() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponseDTO response = productService.getProductById(1L);

        assertEquals("Laptop", response.getProductName());
    }

    @Test
    void getProductById_notFound() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.getProductById(1L));
    }

    // =========================
    // GET ALL (Pagination)
    // =========================

    @Test
    void getAllProducts_success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(java.util.List.of(product));

        when(productRepository.findAll(pageable))
                .thenReturn(page);

        Page<ProductResponseDTO> response =
                productService.getAllProducts(pageable);

        assertEquals(1, response.getTotalElements());
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void updateProduct_success() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setProductName("Updated Laptop");
        dto.setCreatedBy("admin");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponseDTO response =
                productService.updateProduct(1L, dto);

        assertNotNull(response);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_notFound() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        ProductRequestDTO dto = new ProductRequestDTO();

        assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct(1L, dto));
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void deleteProduct_success() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_notFound() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.deleteProduct(1L));
    }
}