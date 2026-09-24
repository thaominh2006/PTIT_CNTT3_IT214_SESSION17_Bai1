package com.example.session17bai1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Lớp khởi chạy chính của ứng dụng.
 * Annotation {@link EnableCaching} kích hoạt cơ chế Spring Cache (AOP proxy cho @Cacheable, @CacheEvict...).
 */
@SpringBootApplication
@EnableCaching
public class Session17Bai1Application {

    public static void main(String[] args) {
        SpringApplication.run(Session17Bai1Application.class, args);
    }
}
