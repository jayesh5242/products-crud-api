package com.example.Products_CRUD_API.Controller;

import com.example.Products_CRUD_API.Service.ProductService;
import com.example.Products_CRUD_API.dto.ProductRequestDTO;
import com.example.Products_CRUD_API.dto.ProductResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // CREATE Product
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(dto));
    }

    // GET Product BY Id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    // GET ALL Products with PAGINATION
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAll(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                productService.getAllProducts(pageable)
        );
    }

    // UPDATE Product
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto) {

        return ResponseEntity.ok(
                productService.updateProduct(id, dto)
        );
    }

    // DELETE Product
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Product deleted successfully with id: " + id
                )
        );
    }
}