package com.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	// @Query("select u from #{#entityName} u where u.firstname = ?1")
	List<User> findByname(String Name);

	// @Query("select u from #{#entityName} u where u.email = ?1")
	// User findByUsername(String username);

	// @Query("select u from #{#entityName} u where u.user.id = ?1")
	@Override
	Optional<User> findById(String userId);

	Optional<User> findByMobileOrEmail(String mobile, String email);

}
