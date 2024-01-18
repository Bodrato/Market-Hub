package com.markethub.smarkethubback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.markethub.smarkethubback.dao.IAccountDAO;
import com.markethub.smarkethubback.model.Account;

@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private IAccountDAO accountDAO;

	@Override
	@Transactional(readOnly=true)
	public List<Account> findAll() {
		return (List<Account>) accountDAO.findAll();
	}

	@Override
	public Account save(Account account) {
	    Optional<Account> existingAccount = accountDAO.findByEmail(account.getEmail());

	    if (existingAccount.isPresent()) {
	        throw new IllegalStateException("An account already exists with the same email");
	    }

	    accountDAO.save(account);

	    return account;
	}

	@Override
	@Transactional(readOnly=true)
	public Account findByEmail(String email) {
		return accountDAO.findByEmail(email).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Account findById(long id) {
		return accountDAO.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		accountDAO.deleteById(id);
	}

	@Override
	public Account login(LoginObject loginObject) {
		return accountDAO.findByEmailAndPassword(
				loginObject.getEmail(), 
				loginObject.getPassword()
			).orElse(null);
	}
	
	@Override
	public Account update(Account account) {
		accountDAO.save(account);
		return account;
	}

}
