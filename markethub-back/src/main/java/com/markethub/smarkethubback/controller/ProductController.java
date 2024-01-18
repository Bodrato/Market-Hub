package com.markethub.smarkethubback.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.markethub.smarkethubback.model.Category;
import com.markethub.smarkethubback.model.Product;
import com.markethub.smarkethubback.service.IProductService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            product.setIdProduct(id);
            Product updatedProduct = productService.update(product);

            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the product");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<List<Category>> getAllCategoriesByProductId(@PathVariable Long id) {
        List<Category> categories = productService.getAllCategoriesByProductId(id);
        return categories != null ? ResponseEntity.ok(categories) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{idProduct}/addCategory/{idCategory}")
    public ResponseEntity<Void> addCategoryToProduct(@PathVariable Long idProduct, @PathVariable Long idCategory) {
        productService.addCategoryToProduct(idProduct, idCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}