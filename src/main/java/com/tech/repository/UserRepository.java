package com.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	
	User findByEmail(String username);
	
	User findByResetPasswordToken(String token);

}
