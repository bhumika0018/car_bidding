package com.carsale.car.services.admin;

import java.io.IOException;

import java.util.List;

import com.carsale.car.dto.CarDTO;
import com.carsale.car.dto.SearchCarDTO;
//import com.carsale.car.dto.UserDTO;
//import com.carsale.car.entity.User;

public interface AdminService {
	boolean createCar(CarDTO carDTO) throws IOException;

	List<CarDTO> getAllCars();

	CarDTO getCarById(Long id);

	void deleteCar(Long id);

	boolean updateCar(Long id, CarDTO carDTO) throws IOException;

	List<SearchCarDTO> searchCar(SearchCarDTO searchCarDTO);

//	List<BidFeignClient> getBids();
//	List<BidFeignClient> getBidsByUserId(Long userId);
//	List<BidFeignClient> getBidsByCarId(Long carId);
//	List<UserDTO> getAllUsers();
}
