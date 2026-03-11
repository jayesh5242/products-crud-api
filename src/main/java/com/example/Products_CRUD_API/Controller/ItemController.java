package com.example.Products_CRUD_API.Controller;

import com.example.Products_CRUD_API.Service.ItemService;
import com.example.Products_CRUD_API.dto.ItemRequestDTO;
import com.example.Products_CRUD_API.dto.ItemResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/{productId}/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

@PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@PathVariable Long productId ,
                                                      @Valid @RequestBody ItemRequestDTO dto){
        return ResponseEntity .status(HttpStatus.CREATED)
                .body(itemService.addItemToProduct(productId,dto));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getItems(@PathVariable Long productId){
        return  ResponseEntity.ok(itemService.getItemsByProductId(productId));
    }

}
