package com.carsale.auth.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.carsale.auth.dto.SignupRequest;
import com.carsale.auth.dto.UserDTO;
import com.carsale.auth.entity.User;

public interface AuthService {

	UserDTO signup(SignupRequest signupRequest);

	Boolean hasUserWithEmail(String email);

	UserDetailsService userDetailsService();

	User getProfile(String name);

	User updateProfile(Long userId, User details);

	User getUserById(Long userId);

	UserDTO getUserByEmail(String username);

	User getUserByName(String username);

	List<UserDTO> getAllCustomers();

	boolean deleteUser(Long userId);

	
}
