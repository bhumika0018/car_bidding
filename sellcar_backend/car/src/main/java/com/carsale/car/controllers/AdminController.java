package com.carsale.car.controllers;
import java.io.IOException;


import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carsale.car.dto.CarDTO;
import com.carsale.car.dto.SearchCarDTO;
import com.carsale.car.services.admin.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {
	
	private final AdminService adminService;
	
	@PostMapping("/car")
	public ResponseEntity<?> addCar(@RequestBody CarDTO carDTO) throws IOException {
		System.out.println("cardto"+ carDTO);
		boolean success=adminService.createCar(carDTO);
		System.out.println("success "+success);
		if(success) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/cars")
	public ResponseEntity<List<CarDTO>> getAllCars(){
		List<CarDTO> cars = adminService.getAllCars();
	    
	    if (cars.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cars);
	    }

	    return ResponseEntity.ok(cars);
	}
	
	@GetMapping("/car/{carId}")
	public ResponseEntity<CarDTO> getCarById(@PathVariable Long carId){
		return ResponseEntity.ok(adminService.getCarById(carId));
	}
	
	@DeleteMapping("/car/{carId}")
	public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
		adminService.deleteCar(carId);
		return ResponseEntity.ok(null);
	}

	@PutMapping("/car/{carId}")
	public ResponseEntity<Boolean> updateCar(@PathVariable Long carId,@RequestBody CarDTO carDTO) throws IOException{
		System.out.println("Car dto "+ carDTO);
		boolean success=adminService.updateCar(carId,carDTO);
		if(success) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PostMapping("/car/search")
	public ResponseEntity<List<SearchCarDTO>> searchCar(@RequestBody SearchCarDTO searchCarDTO) {
        return ResponseEntity.ok(adminService.searchCar(searchCarDTO));
    }
}

