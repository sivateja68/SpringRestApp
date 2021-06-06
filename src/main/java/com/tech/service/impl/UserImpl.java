package com.tech.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.model.User;
import com.tech.repository.UserRepository;
import com.tech.service.IUserService;

@Service
public class UserImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repo.save(user);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByEmail(username);		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),user.getPassword(),new ArrayList<>());
	}
	
	
	@Override
	public void updateUser(User use) {
		repo.save(use);
	}
	@Override
	public Optional<User> getOneUser(Integer id) {
		return repo.findById(id);
	}
	@Override
	public boolean isExist(Integer id) {
		return repo.existsById(id);
	}


	@Override
	public List<User> getAllUsers() {
		return repo.findAll();
	}


	@Override
	public void updateResetPasswordToken(String token, String email) {
		User user = repo.findByEmail(email);
		if(user != null) {
			user.setResetPasswordToken(token);
			repo.save(user);
		}else {
			throw new RuntimeException("User Not found with this email "+ email);
		}	
	}

	@Override
	public User getByResetPasswordToken(String token) {
		return repo.findByResetPasswordToken(token);
	}


	@Override
	public void updatePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setResetPasswordToken(null);
		repo.save(user);
	}
	
}
