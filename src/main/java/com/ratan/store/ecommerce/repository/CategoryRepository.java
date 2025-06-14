package com.ratan.store.ecommerce.repository;

import com.ratan.store.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByName(String name);
}
