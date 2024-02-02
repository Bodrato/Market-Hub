package com.markethub.smarkethubback.service;

import java.util.List;

import com.markethub.smarkethubback.model.Bill;

public interface IBillService {
    public Bill save(Bill bill);

    public Bill findById(Long id);

    public List<Bill> findAll();

    public void invalidate(Long id);
    
    public Bill generateBill(long productId, long userId);
}
