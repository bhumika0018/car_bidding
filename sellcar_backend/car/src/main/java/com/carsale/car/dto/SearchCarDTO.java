package com.carsale.car.dto;

import lombok.Data;

@Data
public class SearchCarDTO {
	private Long carId;
	private String name;
	private String brand;
	private String type;
	private String transmission;
	private String color;
	private Long price;
	
}
