package com.pragma.powerup.domain.plate.model;

import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.plate.exception.NumberRangException;

public class PlateModel {

    private Long id;
    private String name;
    private Long idCategory;
    private String description;
    private Long price;
    private Long idRestaurant;
    private String urlPicture;
    private Boolean active;
    private CategoryModel category;


    public PlateModel(Long id, String name, Long idCategory, String description, Long price, Long idRestaurant, String urlPicture, Boolean active) {
        validateNumberPositive(price);
        this.id = id;
        this.name = name;
        this.idCategory = idCategory;
        this.description = description;
        this.price = price;
        this.idRestaurant = idRestaurant;
        this.urlPicture = urlPicture;
        this.active = active;
    }

    public PlateModel(Long id, String name, Long idCategory, String description, Long price, Long idRestaurant, String urlPicture, Boolean active, CategoryModel category) {
        this.id = id;
        this.name = name;
        this.idCategory = idCategory;
        this.description = description;
        this.price = price;
        this.idRestaurant = idRestaurant;
        this.urlPicture = urlPicture;
        this.active = active;
        this.category = category;
    }

    public PlateModel() {
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

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    private void validateNumberPositive(Long number){
        if(number < 1) {
            throw new NumberRangException("number "+number);
        }
    }
}
