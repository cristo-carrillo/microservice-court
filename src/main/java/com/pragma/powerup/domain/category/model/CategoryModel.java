package com.pragma.powerup.domain.category.model;

import com.pragma.powerup.domain.plate.model.PlateModel;

import java.util.List;

public class CategoryModel {

    private Long id;
    private String name;
    private String description;
    private List<PlateModel> plates;

    public CategoryModel(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CategoryModel(Long id, String name, String description, List<PlateModel> plates) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.plates = plates;
    }

    public CategoryModel() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PlateModel> getPlates() {
        return plates;
    }

    public void setPlates(List<PlateModel> plates) {
        this.plates = plates;
    }
}
