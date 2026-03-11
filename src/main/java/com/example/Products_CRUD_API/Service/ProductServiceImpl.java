package com.example.Products_CRUD_API.Service;

import com.example.Products_CRUD_API.dto.ProductRequestDTO;
import com.example.Products_CRUD_API.dto.ProductResponseDTO;
import com.example.Products_CRUD_API.entity.Product;
import com.example.Products_CRUD_API.exception.ResourceNotFoundException;
import com.example.Products_CRUD_API.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ✅ CREATE Product
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = toEntity(dto);
        product.setCreatedOn(LocalDateTime.now());

        Product saved = productRepository.save(product);
        return toResponseDTO(saved);
    }

    //  Product By ID

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id " + id));

        return toResponseDTO(product);
    }

    // GET All Product (PAGINATION)

    @Override
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    // UPDATE
    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id " + id));

        product.setProductName(dto.getProductName());
        product.setModifiedBy(dto.getCreatedBy());
        product.setModifiedOn(LocalDateTime.now());

        return toResponseDTO(productRepository.save(product));
    }

    // DELETE
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id " + id));

        productRepository.delete(product);
    }


    // MAPPING METHODS


    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setCreatedBy(dto.getCreatedBy());
        return product;
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setCreatedBy(product.getCreatedBy());
        dto.setCreatedOn(product.getCreatedOn());
        dto.setModifiedBy(product.getModifiedBy());
        dto.setModifiedOn(product.getModifiedOn());
        return dto;
    }
}