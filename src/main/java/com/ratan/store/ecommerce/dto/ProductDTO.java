package com.ratan.store.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ratan.store.ecommerce.model.Category;
import com.ratan.store.ecommerce.model.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    private Category category;

    private List<ImageDTO> images;

}
