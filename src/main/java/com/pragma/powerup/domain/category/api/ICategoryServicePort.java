package com.pragma.powerup.domain.category.api;

import com.pragma.powerup.domain.category.model.CategoryModel;

import java.util.List;

public interface ICategoryServicePort {
    void saveCategory(CategoryModel categoryModel);

    List<CategoryModel> getAllCategorys();

    CategoryModel getCategory(Long id);
}
