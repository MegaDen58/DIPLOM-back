package com.example.diplom.service;

import com.example.diplom.dto.ProductDto;
import com.example.diplom.model.Product;
import com.example.diplom.model.User;
import com.example.diplom.repository.ProductRepository;
import com.example.diplom.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            ProductDto productDto = convertToDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public Product saveProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productRepository.save(savedProduct);
    }

    public Product getProductByImage(String image){
        return productRepository.findProductByImage(image);
    }

    public List<Product> getUserFavourites(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // Получаем список избранных продуктов пользователя
            List<Integer> favoriteIds = user.getFavourites();
            List<Product> favoriteProducts = new ArrayList<>();
            for (Integer productId : favoriteIds) {
                // В данном примере просто создаем объект ProductDto с id продукта
                Product product = productRepository.findById(Long.valueOf(productId)).orElseThrow(() -> new RuntimeException("Product not found"));
                favoriteProducts.add(product);
            }
            return favoriteProducts;
        } else {
            return new ArrayList<>();
        }
    }

    public List<String> uploadImages(MultipartFile[] files) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            file.transferTo(new File(filePath));
            imagePaths.add(fileName);
        }
        return imagePaths;
    }

    public void setProductPrice(Long productId, Integer price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setPrice(price);
        logger.info(product.toString());
        productRepository.save(product);
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.orElse(null);
    }

    public Product updateProduct(Long productId, ProductDto productDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setColor(productDto.getColor());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setSize(productDto.getSize());
        existingProduct.setMaterial(productDto.getMaterial());
        existingProduct.setWinter(productDto.isWinter());
        existingProduct.setSummer(productDto.isSummer());
        existingProduct.setImages(productDto.getImages());

        return productRepository.save(existingProduct);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setColor(product.getColor());
        productDto.setWinter(product.isWinter());
        productDto.setSize(product.getSize());
        productDto.setPrice(product.getPrice());
        productDto.setMaterial(product.getMaterial());
        productDto.setSummer(product.isSummer());
        productDto.setImages(product.getImages());
        return productDto;
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setColor(productDto.getColor());
        product.setPrice(productDto.getPrice());
        product.setSize(productDto.getSize());
        product.setMaterial(productDto.getMaterial());
        product.setWinter(productDto.isWinter());
        product.setSummer(productDto.isSummer());
        product.setImages(productDto.getImages());
        return product;
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
