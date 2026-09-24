package com.example.session17bai1.dto;

import java.io.Serializable;

public class DishDTO implements Serializable {

    private Long id;
    private String name;
    private Double price;

    public DishDTO() {
    }

    public DishDTO(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
