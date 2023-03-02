package com.pragma.powerup.infrastructure.category.out.jpa.adapter;

import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.category.spi.ICategoryPersistencePort;
import com.pragma.powerup.infrastructure.category.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.category.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.category.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    @Override
    public CategoryModel saveCategory(CategoryModel categoryModel) {
        CategoryEntity categoryEntity = categoryRepository.save(categoryEntityMapper.toEntity(categoryModel));
        return categoryEntityMapper.toCategoryModel(categoryEntity);
    }

    @Override
    public List<CategoryModel> getAllCategorys() {
        List<CategoryEntity> entityList = categoryRepository.findAll();
        if (entityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toCategoryModelList(entityList);
    }

    @Override
    public CategoryModel getCategory(Long id) {
        Optional<CategoryEntity> entityList = categoryRepository.findById(id);
        if (entityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toCategoryModel(entityList.get());
    }
}
