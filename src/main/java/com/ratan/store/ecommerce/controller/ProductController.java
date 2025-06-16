package com.ratan.store.ecommerce.controller;

import com.ratan.store.ecommerce.Response.ApiResponse;
import com.ratan.store.ecommerce.dto.ProductDTO;
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
            var productList = productService.convertToDTOList(productService.getAllProducts());

            return ResponseEntity.ok(new ApiResponse("SUCCESS", productList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            var product = productService.convertToDTO(productService.getProductById(productId));
            return ResponseEntity.ok(new ApiResponse("SUCCESS", product));
        }catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            var productAdded = productService.convertToDTO(productService.addProduct(product));

            return ResponseEntity.ok(new ApiResponse(("SUCCESS"), productAdded));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductsUpdateRequest product, @PathVariable Long productId) {
        try{
            var updatedProduct = productService.convertToDTO(productService.updateProduct(product, productId));
            return ResponseEntity.ok(new ApiResponse(("SUCCESS"), updatedProduct));
        }catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse(("SUCCESS"), productId));
        }catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/brand/{brandName}")
    public ResponseEntity<ApiResponse> getProductByBrandName(@PathVariable String brandName) {

        try {
            var productList = productService.convertToDTOList(productService.getProductsByBrand(brandName));
            if(productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(("NOT_FOUND"), productList));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", productList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/getBy/BrandAndName")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam  String productName) {
        try {
            var productList = productService.convertToDTOList(productService.getProductByBrandAndName(brandName, productName));
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NOT_FOUND",null));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", productList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/getBy/categoryAndBrand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam  String brand) {
        try {
            var productList = productService.convertToDTOList(productService.getProductsByCategoryAndBrand(category, brand));
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NOT_FOUND",null));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", productList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {

        try {
            var productList = productService.convertToDTOList(productService.getProductsByCategory(category));
            if(productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(("NOT_FOUND"), productList));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", productList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {

        try {
            var productList = productService.convertToDTOList(productService.getProductByName(name));
            if(productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(("NOT_FOUND"), productList));
            }
            return ResponseEntity.ok(new ApiResponse("SUCCESS", productList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }





}
