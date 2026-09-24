package com.example.session17bai1.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.session17bai1.dto.DishDTO;
import com.example.session17bai1.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable(value = "restaurantMenu", key = "#restaurantId")
    public List<DishDTO> getMenuByRestaurantId(Long restaurantId) {
        return restaurantRepository.findMenuByRestaurantId(restaurantId);
    }
}
