package com.markethub.smarkethubback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.markethub.smarkethubback.dao.IAccountDAO;
import com.markethub.smarkethubback.dao.IBillDAO;
import com.markethub.smarkethubback.dao.IProductDAO;
import com.markethub.smarkethubback.model.Account;
import com.markethub.smarkethubback.model.Bill;
import com.markethub.smarkethubback.model.Product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BillService implements IBillService {

    @Autowired
    private IBillDAO billDAO;
    
    @Autowired
    private IProductDAO productDAO;
    
    @Autowired
    private IAccountDAO accountDAO;

    @Override
    public Bill save(Bill bill) {
        return billDAO.save(bill);
    }

    @Override
    public Bill findById(Long id) {
        Optional<Bill> optionalBill = billDAO.findById(id);
        return optionalBill.orElseThrow(() -> new EntityNotFoundException("Bill not found for id: " + id));
    }

    @Override
    public List<Bill> findAll() {
        return (List<Bill>) billDAO.findAll();
    }

    @Override
    public void invalidate(Long id) {
        try {
            Bill bill = findById(id);

            if (bill == null) {
                throw new EntityNotFoundException("Bill with id " + id + " not found");
            }

            bill.setValid(false);
            billDAO.save(bill);
        } catch (Exception e) {
            throw new EntityNotFoundException("Error invalidating bill with id " + id, e);
        }
    }

    @Override
    @Transactional
    public Bill generateBill(long productId, long userId) {
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        Account accountBuyer = accountDAO.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + userId));

        Bill bill = new Bill();
        bill.setProduct(product);
        bill.setAccountBuyer(accountBuyer);
        bill.setStatus("PENDING");

        return billDAO.save(bill);
    }
}