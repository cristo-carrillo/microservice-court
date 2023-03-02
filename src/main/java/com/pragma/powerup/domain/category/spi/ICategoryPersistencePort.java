package com.pragma.powerup.domain.category.spi;

import com.pragma.powerup.domain.category.model.CategoryModel;

import java.util.List;

public interface ICategoryPersistencePort {

    CategoryModel saveCategory(CategoryModel categoryModel);

    List<CategoryModel> getAllCategorys();

    CategoryModel getCategory(Long id);
}
