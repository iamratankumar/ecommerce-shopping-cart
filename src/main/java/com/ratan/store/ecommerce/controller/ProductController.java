package com.ratan.store.ecommerce.controller;

import com.ratan.store.ecommerce.Response.ApiResponse;
import com.ratan.store.ecommerce.model.Product;
import com.ratan.store.ecommerce.request.AddProductRequest;
import com.ratan.store.ecommerce.request.ProductsUpdateRequest;
import com.ratan.store.ecommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/product")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try{
            List<Product> productList = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("SUCCESS", productList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("SUCCESS", product));
        }catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product productAdded = productService.addProduct(product);

            return ResponseEntity.ok(new ApiResponse(("SUCCESS"), productAdded));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductsUpdateRequest product, @PathVariable Long productId) {
        try{
            Product updatedProduct = productService.updateProduct(product, productId);
            return ResponseEntity.ok(new ApiResponse(("SUCCESS"), updatedProduct));
        }catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse(("SUCCESS"), productId));
        }catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{brandName}/Name")
    public ResponseEntity<ApiResponse> getProductByBrandName(@PathVariable String brandName) {

        try {
            List<Product> product = productService.getProductsByBrand(brandName);
            if(product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(("NOT_FOUND"), product));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", product));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/getBy/BrandAndName")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam  String productName) {
        try {
            List<Product> productList = productService.getProductByBrandAndName(brandName, productName);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NOT_FOUND",null));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", productList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/getBy/categoryAndBrand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam  String brand) {
        try {
            List<Product> productList = productService.getProductsByCategoryAndBrand(category, brand);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NOT_FOUND",null));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", productList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{category}/category")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {

        try {
            List<Product> product = productService.getProductsByCategory(category);
            if(product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(("NOT_FOUND"), product));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", product));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{name}/name")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {

        try {
            List<Product> product = productService.getProductByName(name);
            if(product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(("NOT_FOUND"), product));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", product));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }





}
