package com.ratan.store.ecommerce.service.product;

import com.ratan.store.ecommerce.dto.ProductDTO;
import com.ratan.store.ecommerce.model.Product;
import com.ratan.store.ecommerce.request.AddProductRequest;
import com.ratan.store.ecommerce.request.ProductsUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductsUpdateRequest product, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String productName);
    List<Product> getProductByBrandAndName(String brand, String productName);
    Long countProductByBrandAndName(String brand, String productName);

    ProductDTO convertToDTO(Product product);

    List<ProductDTO> convertToDTOList(List<Product> productList);
}
