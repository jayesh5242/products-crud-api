//package com.example.Products_CRUD_API.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.List;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//
//        CorsConfiguration config = new CorsConfiguration();
//
//        // ✅ Allowed origins (ADD file:// for testing)
//        config.setAllowedOrigins(
//                List.of(
//                        "http://localhost:3000",
//                        "file://"
//                )
//        );
//
//        config.setAllowedMethods(
//                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
//        );
//
//        config.setAllowedHeaders(
//                List.of("Authorization", "Content-Type")
//        );
//
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsFilter(source);
//    }
//}
package com.example.Products_CRUD_API.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;

import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // allow all origins for testing
        config.addAllowedOriginPattern("*");

        // allow all headers
        config.addAllowedHeader("*");

        // allow all methods
        config.addAllowedMethod("*");

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}