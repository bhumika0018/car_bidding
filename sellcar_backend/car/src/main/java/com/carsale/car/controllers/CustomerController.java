package com.carsale.car.controllers;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carsale.car.dto.CarDTO;
import com.carsale.car.dto.SearchCarDTO;
import com.carsale.car.services.customer.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin("*")
public class CustomerController {
	
	private final CustomerService customerService;
	
	@GetMapping("/cars")
	public ResponseEntity<List<CarDTO>> getAllCars(){
		return ResponseEntity.ok(customerService.getAllCars());
	}
	
	@GetMapping("/car/{carId}")
	public ResponseEntity<CarDTO> getCarById(@PathVariable Long carId){
		return ResponseEntity.ok(customerService.getCarById(carId));
	}
	
	@PostMapping("/car/search")
	public ResponseEntity<List<SearchCarDTO>> searchCar(@RequestBody SearchCarDTO searchCarDTO) {
        return ResponseEntity.ok(customerService.searchCar(searchCarDTO));
    }
	
}
