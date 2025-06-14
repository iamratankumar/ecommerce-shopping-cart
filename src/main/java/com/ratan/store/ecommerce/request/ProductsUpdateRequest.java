package com.ratan.store.ecommerce.request;

import com.ratan.store.ecommerce.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductsUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;


    private Category category;
}
