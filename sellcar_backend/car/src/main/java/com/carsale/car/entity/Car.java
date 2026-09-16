package com.carsale.car.entity;

import java.sql.Date;

import com.carsale.car.dto.CarDTO;
import com.carsale.car.dto.SearchCarDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "car")
@Data
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long carId;
	private String name;
	private String brand;
	private String type;
	private String transmission;
	private String color;
	private Date year;
	private Boolean sold;
	private Long price;

	@Lob
	private String description;

	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] img;

//	getCarDTO() - to convert the entity to CarDTO
	public CarDTO getCarDTO() {
		CarDTO carDTO = new CarDTO();
		carDTO.setCarId(carId);
		carDTO.setName(name);
		carDTO.setBrand(brand);
		carDTO.setType(type);
		carDTO.setTransmission(transmission);
		carDTO.setColor(color);
		carDTO.setYear(year);
		carDTO.setSold(sold);
		carDTO.setDescription(description);
		carDTO.setPrice(price);
//		carDTO.setReturnedImg(img);
		return carDTO;
	}
	
	public SearchCarDTO getSearchCarDTO() {
		SearchCarDTO searchCarDTO = new SearchCarDTO();
//		searchCarDTO.setCarId(carId);
		searchCarDTO.setName(name);
		searchCarDTO.setBrand(brand);
		searchCarDTO.setType(type);
		searchCarDTO.setTransmission(transmission);
		searchCarDTO.setColor(color);
		searchCarDTO.setPrice(price);
		return searchCarDTO;
	}
}
