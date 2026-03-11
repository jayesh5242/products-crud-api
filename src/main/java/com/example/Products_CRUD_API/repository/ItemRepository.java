package com.example.Products_CRUD_API.repository;

import com.example.Products_CRUD_API.entity.Item;
import com.example.Products_CRUD_API.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByProduct(Product product);
}
