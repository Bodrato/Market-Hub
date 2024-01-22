package com.markethub.smarkethubback.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.markethub.smarkethubback.model.Account;
import com.markethub.smarkethubback.model.Product;

public interface IProductDAO extends CrudRepository<Product, Long> {
    List<Product> findByAccount(Account account);
}
