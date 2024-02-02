package com.markethub.smarkethubback.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.markethub.smarkethubback.model.Bill;

public interface IBillDAO extends CrudRepository<Bill, Long>{
	Optional<Bill> findByProduct_IdProduct(Long id);
}
