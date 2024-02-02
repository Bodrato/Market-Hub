package com.markethub.smarkethubback.service;

import java.util.List;

import com.markethub.smarkethubback.model.Category;
import com.markethub.smarkethubback.model.Product;

public interface ICategoryService {
	public List<Category> findAll();
	public Category save(Category category);
	public Category findById(long id);
	public void delete(Long id);
	public List<Product> getAllProductsByCategoryId(long idCategory);
	public void addProductToCategory(long idProduct, long idCategory);
	public Category update(Category category);
}
