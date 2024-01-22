package com.markethub.smarkethubback.service;

import java.util.List;

import com.markethub.smarkethubback.model.Account;
import com.markethub.smarkethubback.model.Product;

public interface IAccountService {
	public List<Account> findAll();
	public Account save(Account account);
	public Account findByEmail(String email);
	public Account findById(long id);
	public void delete(Long id);
	public Account login(LoginObject loginObject);
	public Account update(Account account);
	public List<Product> getAllProductByAccountId(long id);
}


