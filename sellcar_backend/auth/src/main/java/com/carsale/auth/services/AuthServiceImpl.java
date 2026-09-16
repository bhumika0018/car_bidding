package com.carsale.auth.services;

import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carsale.auth.dto.SignupRequest;
import com.carsale.auth.dto.UserDTO;
import com.carsale.auth.entity.User;
import com.carsale.auth.enums.UserRole;
import com.carsale.auth.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;

	@PostConstruct
	public void createAnAdminAccount() {
		Optional<User> optionalAdmin = userRepository.findByUserRole(UserRole.ADMIN);
		if (optionalAdmin.isEmpty()) {
			User admin = new User();
			admin.setName("Admin");
			admin.setEmail("admin@test.com");
			admin.setUserRole(UserRole.ADMIN);
			admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(admin);
			System.out.println("Admin account created successfully");
		} else {
			System.out.println("Admin account already exist!");
		}
	}

	public Boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}

	@Override
	public UserDTO signup(SignupRequest signupRequest) {
		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setUserRole(UserRole.CUSTOMER);
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		return userRepository.save(user).getUserDTO();
	}

	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				return userRepository.findFirstByEmail(email)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			}
		};
	}

	// get user profile by username
	public User getProfile(String name) {
		return userRepository.findByName(name)
				.orElseThrow(() -> new RuntimeException("User with username " + name + " not found"));

	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));
	}

	@Override
	public User getUserByName(String username) {
		return userRepository.findByName(username).get();
	}

	@Override
	public UserDTO getUserByEmail(String username) {
		System.out.println("is this error "+ username);
		User user = userRepository.findFirstByEmail(username).get();
		return user.getUserDTO();
	}

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	public User updateProfile(Long userId, User details) {
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		if (details.getName() != null && !details.getName().isEmpty()) {
			existingUser.setName(details.getName());
		}

		if (details.getEmail() != null && !details.getEmail().isEmpty()) {
			existingUser.setEmail(details.getEmail());
		}

		if (details.getPassword() != null && !details.getPassword().isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			existingUser.setPassword(passwordEncoder.encode(details.getPassword()));
		}

		userRepository.save(existingUser);
		return null;
	}

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<UserDTO> getAllCustomers() {

		List<User> customers = userRepository.findAllByUserRole(UserRole.CUSTOMER);
		return customers.stream().map(User::getUserDTO).toList();
	}
	
	public boolean deleteUser(Long userId) {
	    Optional<User> userOptional = userRepository.findById(userId);
	    if (userOptional.isPresent()) {
	        userRepository.deleteById(userId);
	        return true;
	    }
	    return false;
	}

}
