package com.pragma.powerup.domain.category.usecase;

import com.pragma.powerup.domain.category.api.ICategoryServicePort;
import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.category.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(CategoryModel categoryModel) {
        categoryPersistencePort.saveCategory(categoryModel);

    }

    @Override
    public List<CategoryModel> getAllCategorys() {
        return categoryPersistencePort.getAllCategorys();
    }

    @Override
    public CategoryModel getCategory(Long id) {
        return categoryPersistencePort.getCategory(id);
    }


}
