package com.carsale.auth.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carsale.auth.dto.AuthenticationRequest;
import com.carsale.auth.dto.AuthenticationResponse;
import com.carsale.auth.dto.SignupRequest;
import com.carsale.auth.dto.UserDTO;
import com.carsale.auth.entity.User;
import com.carsale.auth.repository.UserRepository;
import com.carsale.auth.services.AuthService;
import com.carsale.auth.services.utils.JWTUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	private final AuthService authService;
	private final JWTUtil jwtUtil;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
		if (authService.hasUserWithEmail(signupRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists");
		}
		UserDTO userDTO = authService.signup(signupRequest);
		if (userDTO == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
	}

	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password");
		}
		final UserDetails userDetails = authService.userDetailsService()
				.loadUserByUsername(authenticationRequest.getEmail());
		Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			String token = jwtUtil.generateToken(userDetails, user.getUserRole().name());
			AuthenticationResponse response = new AuthenticationResponse();
			response.setJwt(token);
			response.setUserRole(user.getUserRole());
			response.setUserId(user.getId());
			return response;
		}
		throw new RuntimeException("User not found.");
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserDTO> getprofile(@PathVariable String username) {
		// Fetches email id
		String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		String authenticatedRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		Optional<User> userWithEmail=userRepository.findByName(username);
		String email = userWithEmail.map(User::getEmail).orElse(null);
		if (authenticatedRole.contains("ADMIN") || email.equals(authenticatedUser)) {
			try {
				User user = authService.getProfile(username);
				return ResponseEntity.ok(user.getUserDTO());
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
	}

	@GetMapping("/auth")
	public String validateToken() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDTO user = authService.getUserByEmail(username);
		System.out.println("end point of validateToken()");

		return username + " " + user.getUserRole().toString();
	}

	@GetMapping("/{userId}/role")
	public ResponseEntity<String> getUserRole(@PathVariable Long userId) {
		try {
			User user = authService.getUserById(userId);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
			return ResponseEntity.ok(user.getUserRole().name());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

    // update userprofile
	@PutMapping("/{userId}/update")
	public ResponseEntity<User> updateProfile(@PathVariable Long userId, @RequestBody User details) {
		return ResponseEntity.ok(authService.updateProfile(userId, details));
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/customers")
    public ResponseEntity<List<UserDTO>> getAllCustomers() {
		try {
			List<UserDTO> customers = authService.getAllCustomers();
			if (customers == null) {
				return (ResponseEntity<List<UserDTO>>) ResponseEntity.status(HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok(customers);
		} catch (Exception e) {
			return (ResponseEntity<List<UserDTO>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@DeleteMapping("/{userId}/delete")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
	    try {
	        boolean isDeleted = authService.deleteUser(userId);
	        if (isDeleted) {
	            return ResponseEntity.ok(null);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	    }
	}

	

}
