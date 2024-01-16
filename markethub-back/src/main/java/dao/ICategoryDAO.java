package dao;

import org.springframework.data.repository.CrudRepository;

import com.markethub.smarkethubback.model.Category;

public interface ICategoryDAO extends CrudRepository<Category, Long> {

}
