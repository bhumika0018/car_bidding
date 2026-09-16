package com.carsale.auth.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequest {
	@NotNull(message="Email cannot be null")
	private String email;
	private String name;
	private String password;
}	