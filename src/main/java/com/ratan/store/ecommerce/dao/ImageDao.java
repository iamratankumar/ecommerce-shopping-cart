package com.ratan.store.ecommerce.dao;

import com.ratan.store.ecommerce.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);

}
