package com.markethub.smarkethubback.dao;

import org.springframework.data.repository.CrudRepository;

import com.markethub.smarkethubback.model.Bill;

public interface IBillDAO extends CrudRepository<Bill, Long>{

}
