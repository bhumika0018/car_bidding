package com.carsale.car.services.customer;

import java.util.List;

import com.carsale.car.dto.AnalyticsDTO;
import com.carsale.car.dto.CarDTO;
import com.carsale.car.dto.SearchCarDTO;
import com.carsale.feign.BidFeignClient;

public interface CustomerService {
	
	List<CarDTO> getAllCars();

	CarDTO getCarById(Long id);

	List<SearchCarDTO> searchCar(SearchCarDTO searchCarDTO);

//	List<CarDTO> getMyCars(Long userId);

//	public List<String> fetchBidStatuses();

//	boolean bidACar(BidFeignClient bidDTO);
//
//	List<BidFeignClient> getBidsByUserId(Long userId);
//
//	List<BidFeignClient> getBidsByCarId(Long carId);

//	AnalyticsDTO getAnalytics(Long userId);
}
