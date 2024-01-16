package dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.markethub.smarkethubback.model.Account;

public interface IAccountDAO extends CrudRepository<Account,Long>  {
	Optional<Account> findByEmail(String email);
	Optional<Account> findByEmailAndPassword(String email, String password);

}
