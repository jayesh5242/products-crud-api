package com.example.Products_CRUD_API.Service;

import com.example.Products_CRUD_API.dto.ItemRequestDTO;
import com.example.Products_CRUD_API.dto.ItemResponseDTO;
import com.example.Products_CRUD_API.entity.Item;
import com.example.Products_CRUD_API.entity.Product;
import com.example.Products_CRUD_API.exception.ResourceNotFoundException;
import com.example.Products_CRUD_API.repository.ItemRepository;
import com.example.Products_CRUD_API.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ItemServiceImpl(
            ItemRepository itemRepository,
            ProductRepository productRepository) {

        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    // ADD ITEM TO PRODUCT
    @Override
    public ItemResponseDTO addItemToProduct(Long productId, ItemRequestDTO dto) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id " + productId));

        Item item = new Item();
        item.setQuantity(dto.getQuantity());
        item.setProduct(product);

        return toResponseDTO(itemRepository.save(item));
    }

    // GET ITEMS BY PRODUCT ID
    @Override
    public List<ItemResponseDTO> getItemsByProductId(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id " + productId));

        return itemRepository.findByProduct(product)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ENTITY → DTO MAPPER
    private ItemResponseDTO toResponseDTO(Item item) {

        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setProductId(item.getProduct().getId());
        return dto;
    }
}