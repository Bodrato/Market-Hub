package com.markethub.smarkethubback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.markethub.smarkethubback.dao.IAccountDAO;
import com.markethub.smarkethubback.dao.IProductDAO;
import com.markethub.smarkethubback.model.Account;
import com.markethub.smarkethubback.model.Product;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDAO accountDAO;
    
    @Autowired
    private IProductDAO productDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return (List<Account>) accountDAO.findAll();
    }

    @Override
    @Transactional
    public Account save(Account account) {
        validateUniqueEmail(account.getEmail());
        accountDAO.save(account);
        return account;
    }

    @Override
    @Transactional(readOnly = true)
    public Account findByEmail(String email) {
        return accountDAO.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Account not found with email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public Account findById(long id) {
        return accountDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            accountDAO.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Account not found with id: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Account login(LoginObject loginObject) {
        return accountDAO.findByEmailAndPassword(loginObject.getEmail(), loginObject.getPassword())
                .orElseThrow(() -> new EntityNotFoundException("Account not found for the provided credentials"));
    }

    @Override
    @Transactional
    public Account update(Account account) {
        accountDAO.save(account);
        return account;
    }

    private void validateUniqueEmail(String email) {
        Optional<Account> existingAccount = accountDAO.findByEmail(email);
        if (existingAccount.isPresent()) {
            throw new IllegalStateException("An account already exists with the same email");
        }
    }

    @Override
    public List<Product> getAllProductByAccountId(long id) {
        try {
            Optional<Account> optionalAccount = accountDAO.findById(id);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                return productDAO.findByAccount(account);
            } else {
                throw new EntityNotFoundException("Account with id " + id + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting products by account id: " + id, e);
        }
    }
}