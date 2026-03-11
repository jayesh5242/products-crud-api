package com.example.Products_CRUD_API.Service;

import com.example.Products_CRUD_API.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User register(String username, String password);

    User validateUser(String username, String password);

}