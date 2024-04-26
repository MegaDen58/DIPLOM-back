package com.example.diplom.controller;

import com.example.diplom.dto.ProductDto;
import com.example.diplom.dto.ProductPrice;
import com.example.diplom.model.Product;
import com.example.diplom.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Value("${file.upload-dir}")
    private String uploadDir;
    private final ProductService productService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public ProductController(ProductService productService, ResourceLoader resourceLoader) {
        this.productService = productService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{imageName}")
    public ResponseEntity<Product> getProductByImage(@PathVariable String imageName) {
        Product product = productService.getProductByImage(imageName);
        return ResponseEntity.ok(product);
    }
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product savedProduct = productService.saveProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/user/{userId}/favourites")
    public List<Product> getUserFavorites(@PathVariable Long userId) {
        return productService.getUserFavourites(userId);
    }

    @GetMapping("/{productId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long productId) {
        try {
            // Путь к папке с изображениями
            String imagePath = "classpath:/uploads/images/" + productId + ".png";
            Resource resource = resourceLoader.getResource(imagePath);
            Path filePath = Paths.get(resource.getURI());
            byte[] imageBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("No image uploaded");
        }
        try {
            // Генерируем уникальное имя для изображения
            String fileName = image.getOriginalFilename();
            logger.info(fileName);
            // Полный путь для сохранения файла на сервере
            Path path = Paths.get(uploadDir + fileName);

            // Сохраняем файл на сервере
            Files.write(path, image.getBytes());

            // Возвращаем URL загруженного изображения
            String imageUrl = "http://94.228.112.46:8080/app/src/main/resources/uploads/images/" + fileName;
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<byte[]> getImageByName(@PathVariable String imageName) {
        try {
            // Формируем путь к файлу внутри контейнера
            String imagePath = "/app/src/main/resources/uploads/images/" + imageName;
            Resource resource = resourceLoader.getResource("file:" + imagePath);
            InputStream inputStream = resource.getInputStream();
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/setPrice")
    public ResponseEntity<String> setProductPrice(@RequestBody ProductPrice request) {
        productService.setProductPrice(request.getProductId(), request.getPrice());

        return ResponseEntity.ok("Price set successfully.");
    }

    @PostMapping("/{productId}/delete")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
