package com.markethub.smarkethubback.dao;

import org.springframework.data.repository.CrudRepository;

import com.markethub.smarkethubback.model.Product;

public interface IProductDAO extends CrudRepository<Product, Long> {

}
