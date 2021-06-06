package com.tech.service;

import java.util.List;
import java.util.Optional;

import com.tech.model.User;

public interface IUserService {

	User save(User user);
	void updateUser(User use);
	boolean isExist(Integer id);
	Optional<User> getOneUser(Integer id);
	List<User> getAllUsers();
	void updateResetPasswordToken(String token, String email);
	User getByResetPasswordToken(String token);
	void updatePassword(User user, String newPassword);
}
