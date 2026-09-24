package com.example.session17bai1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.session17bai1.dto.DishDTO;
import com.example.session17bai1.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{restaurantId}/menu")
    public List<DishDTO> getMenu(@PathVariable Long restaurantId) {
        return restaurantService.getMenuByRestaurantId(restaurantId);
    }
}
