package com.example.session17bai1.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.session17bai1.dto.DishDTO;

@Repository
public class RestaurantRepository {

    public List<DishDTO> findMenuByRestaurantId(Long restaurantId) {
        simulateSlowDatabaseQuery();

        return List.of(
                new DishDTO(1L, "Pho bo", 45000.0),
                new DishDTO(2L, "Bun cha", 40000.0),
                new DishDTO(3L, "Com tam", 35000.0)
        );
    }

    private void simulateSlowDatabaseQuery() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Truy van CSDL bi gian doan", e);
        }
    }
}
