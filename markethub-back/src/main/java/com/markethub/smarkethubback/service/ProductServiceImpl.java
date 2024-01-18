package com.markethub.smarkethubback.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.markethub.smarkethubback.dao.ICategoryDAO;
import com.markethub.smarkethubback.dao.IProductDAO;
import com.markethub.smarkethubback.model.Category;
import com.markethub.smarkethubback.model.Product;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDAO productDAO;
    
    @Autowired
    private ICategoryDAO categoryDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productDAO.findAll();
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productDAO.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productDAO.deleteById(id);
    }

    @Override
    @Transactional
    public Product update(Product updatedProduct) {
        Objects.requireNonNull(updatedProduct.getIdProduct(), "Product ID must not be null");

        Product existingProduct = productDAO.findById(updatedProduct.getIdProduct())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + updatedProduct.getIdProduct()));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setVisible(updatedProduct.getVisible());
        existingProduct.setAccount(updatedProduct.getAccount());

        return productDAO.save(existingProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategoriesByProductId(long idProduct) {
        Product product = productDAO.findById(idProduct).orElse(null);

        if (product != null) {
            Set<Category> categories = product.getCategories();
            return new ArrayList<>(categories);
        } else {
            return Collections.emptyList();
        }
    }
    
    @Override
    @Transactional
    public void addCategoryToProduct(long idProduct, long idCategory) {
        Product product = productDAO.findById(idProduct).orElse(null);
        Category category = categoryDAO.findById(idCategory).orElse(null);

        if (product != null && category != null) {
            product.getCategories().add(category);
            productDAO.save(product);
        }
    }

}