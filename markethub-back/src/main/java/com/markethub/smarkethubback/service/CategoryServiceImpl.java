package com.markethub.smarkethubback.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryDAO categoryDAO;

    @Autowired
    private IProductDAO productDAO;

    @Override
    @Transactional(readOnly=true)
    public List<Category> findAll() {
        return (List<Category>) categoryDAO.findAll();
    }

    @Override
    @Transactional
    public Category save(Category category) {
        return categoryDAO.save(category);
    }

    @Override
    @Transactional(readOnly=true)
    public Category findById(long id) {
        return categoryDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProductsByCategoryId(long idCategory) {
        Category category = categoryDAO.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + idCategory));

        Set<Product> products = category.getProducts();
        return new ArrayList<>(products);
    }

    @Override
    @Transactional
    public void addProductToCategory(long idProduct, long idCategory) {
        Product product = productDAO.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + idProduct));
        Category category = categoryDAO.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + idCategory));

        category.getProducts().add(product);
        categoryDAO.save(category);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        Category existingCategory = categoryDAO.findById(category.getIdCategory())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + category.getIdCategory()));

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return categoryDAO.save(existingCategory);
    }
}

	
	
	

