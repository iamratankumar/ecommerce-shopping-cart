package com.ratan.store.ecommerce.service.category;

import com.ratan.store.ecommerce.exceptions.AlreadyExistsException;
import com.ratan.store.ecommerce.exceptions.ResourceNotFoundException;
import com.ratan.store.ecommerce.model.Category;
import com.ratan.store.ecommerce.dao.CategoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryDao categoryDao;
    @Override
    public Category getCategoryById(Long id) {
        return categoryDao.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryDao.findByName(name);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c-> !categoryDao.existsByName(c.getName()))
                .map(categoryDao::save).orElseThrow(()-> new AlreadyExistsException(category.getName()+ " already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory ->{
                    oldCategory.setName(category.getName());
                    return categoryDao.save(oldCategory);
                }).orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryDao.findById(id)
                .ifPresentOrElse(categoryDao::delete, () -> {
                    throw new ResourceNotFoundException("Category not found!");
                });
    }
}
