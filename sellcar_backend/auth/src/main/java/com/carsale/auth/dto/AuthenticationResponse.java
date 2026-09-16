package com.carsale.auth.dto;

import com.carsale.auth.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private String jwt;
	private Long userId;
	private UserRole userRole;
}

