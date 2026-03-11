package com.example.Products_CRUD_API.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncAuditService {

    @Async
    public void logUserAction(String message) {
        System.out.println("ASYNC LOG START");
        System.out.println("Thread Name = " + Thread.currentThread().getName());
        System.out.println(message);
        System.out.println("ASYNC LOG END");
    }
}
