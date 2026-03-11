package com.example.Products_CRUD_API.repository;
import com.example.Products_CRUD_API.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long>
{

}
