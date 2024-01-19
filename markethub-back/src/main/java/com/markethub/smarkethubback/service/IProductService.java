package com.markethub.smarkethubback.service;

import java.util.List;

import com.markethub.smarkethubback.model.Bill;
import com.markethub.smarkethubback.model.Category;
import com.markethub.smarkethubback.model.Product;

public interface IProductService {
	public List<Product> findAll();
	
	public Product save(Product product);
	
	public Product findById(long id);
	
	public void delete(Long id);
	
	public Product update(Product updatedProduct);
	
	public List<Category> getAllCategoriesByProductId(long idProduct);
	
	public void addCategoryToProduct(long idProduct, long idCategory);
	
	public Bill getBillByProductId(long idProduct);
}
