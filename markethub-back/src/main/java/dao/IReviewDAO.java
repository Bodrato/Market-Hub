package dao;

import org.springframework.data.repository.CrudRepository;

import com.markethub.smarkethubback.model.Review;

public interface IReviewDAO extends CrudRepository<Review, Long>{

}
