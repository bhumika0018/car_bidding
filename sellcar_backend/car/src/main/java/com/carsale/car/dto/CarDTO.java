package com.carsale.car.dto;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CarDTO {
	private Long carId;
	private String name;
	private String brand;
	private String type;
	private String transmission;
	private String color;
	private Date year;
	private Boolean sold;
	private Long price;
	private String description;
	private MultipartFile img;
	private byte[] returnedImg;
}
