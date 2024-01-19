package com.markethub.smarkethubback.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.markethub.smarkethubback.dao.IBillDAO;
import com.markethub.smarkethubback.dao.ICategoryDAO;
import com.markethub.smarkethubback.dao.IProductDAO;
import com.markethub.smarkethubback.model.Bill;
import com.markethub.smarkethubback.model.Category;
import com.markethub.smarkethubback.model.Product;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDAO productDAO;
    
    @Autowired
    private ICategoryDAO categoryDAO;
    
    @Autowired
    private IBillDAO billDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(long id) {
        return productDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }
    
    @Override
    @Transactional
    public Product save(Product product) {
        return productDAO.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            productDAO.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
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
        existingProduct.setAccount(updatedProduct.getAccount());

        return productDAO.save(existingProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategoriesByProductId(long idProduct) {
        Product product = productDAO.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + idProduct));

        Set<Category> categories = product.getCategories();
        return new ArrayList<>(categories);
    }

    @Override
    @Transactional
    public void addCategoryToProduct(long idProduct, long idCategory) {
        Product product = productDAO.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + idProduct));
        Category category = categoryDAO.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + idCategory));

        product.getCategories().add(category);
        productDAO.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Bill getBillByProductId(long idProduct) {
        Optional<Bill> optionalBill = billDAO.findByProduct_IdProduct(idProduct);

        return optionalBill.orElseThrow(() -> new EntityNotFoundException("Bill not found for productId: " + idProduct));
    }
}