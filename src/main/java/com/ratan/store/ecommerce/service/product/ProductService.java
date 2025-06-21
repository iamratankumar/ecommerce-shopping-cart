package com.ratan.store.ecommerce.service.product;

import com.ratan.store.ecommerce.dto.ImageDTO;
import com.ratan.store.ecommerce.dto.ProductDTO;
import com.ratan.store.ecommerce.exceptions.ResourceNotFoundException;
import com.ratan.store.ecommerce.model.Category;
import com.ratan.store.ecommerce.model.Image;
import com.ratan.store.ecommerce.model.Product;
import com.ratan.store.ecommerce.repository.CategoryRepository;
import com.ratan.store.ecommerce.repository.ImageRepository;
import com.ratan.store.ecommerce.repository.ProductRepository;
import com.ratan.store.ecommerce.request.AddProductRequest;
import com.ratan.store.ecommerce.request.ProductsUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService  implements IProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {


        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        request.setCategory(category);
        return repository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest product, Category category) {
        return  new Product(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete,
                ()-> {
                    throw new ResourceNotFoundException("Product not found!");
                });
    }

    @Override
    public Product updateProduct(ProductsUpdateRequest product, Long id) {

        return productRepository.findById(id)
                        .map(exsitingProduct -> updateProduct(exsitingProduct, product))
                .map(productRepository::save)
                .orElseThrow(()->new ResourceNotFoundException("Product not found!"));
    }
    private Product updateProduct(Product product, ProductsUpdateRequest request) {
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());
        product.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        product.setCategory(category);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return repository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return repository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return repository.findByName(productName);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String productName) {
        return repository.findByBrandAndName(brand,productName);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String productName) {
        return repository.countByBrandAndName(brand,productName);
    }

    @Override
    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<Image> imageList = imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDTOList= imageList.stream().map(
                image-> modelMapper.map(image, ImageDTO.class))
                .toList();
        productDTO.setImages(imageDTOList);
        return productDTO;
    }

    @Override
    public List<ProductDTO> convertToDTOList(List<Product> productList){
        return productList.stream().map(this::convertToDTO).toList();
    }
}
