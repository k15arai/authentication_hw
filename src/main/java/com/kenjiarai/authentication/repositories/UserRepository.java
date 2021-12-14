package com.kenjiarai.authentication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kenjiarai.authentication.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	List<User> findAll();
	
	User findByEmail(String email);
	
	// Native Query to count how many users there are
	@Query(value = "SELECT COUNT(id) FROM users", nativeQuery = true)
	int getCount();
	
}
