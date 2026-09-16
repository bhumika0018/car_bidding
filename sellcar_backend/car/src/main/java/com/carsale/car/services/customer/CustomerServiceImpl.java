package com.carsale.car.services.customer;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carsale.car.dto.AnalyticsDTO;
import com.carsale.car.dto.CarDTO;
import com.carsale.car.dto.SearchCarDTO;
import com.carsale.car.entity.Car;
import com.carsale.car.repository.CarRepository;
//import com.carsale.feign.AuthFeignClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	private final CarRepository carRepository;
//	private final BidRepository bidRepository;
//	@Autowired
//	BidFeignClient bidFeignClient;

	
	
	@Override
	public List<CarDTO> getAllCars() {
		return carRepository.findAll().stream().map(Car::getCarDTO).collect(Collectors.toList());
	}

	@Override
	public CarDTO getCarById(Long id) {
		Optional<Car> optionalCar = carRepository.findById(id);
		return optionalCar.map(Car::getCarDTO).orElse(null);
	}

	@Override
	public List<SearchCarDTO> searchCar(SearchCarDTO searchCarDTO) {
	    List<Car> cars = carRepository.findAll();

	    // Apply search filters based on the provided DTO fields
	    if (searchCarDTO.getName() != null) {
	        cars = cars.stream()
	                .filter(car -> searchCarDTO.getName().equalsIgnoreCase(Optional.ofNullable(car.getName()).orElse("")))
	                .collect(Collectors.toList());
	    }
	    if (searchCarDTO.getBrand() != null) {
	        cars = cars.stream()
	                .filter(car -> searchCarDTO.getBrand().equalsIgnoreCase(Optional.ofNullable(car.getBrand()).orElse("")))
	                .collect(Collectors.toList());
	    }
	    if (searchCarDTO.getType() != null) {
	        cars = cars.stream()
	                .filter(car -> searchCarDTO.getType().equalsIgnoreCase(Optional.ofNullable(car.getType()).orElse("")))
	                .collect(Collectors.toList());
	    }
	    if (searchCarDTO.getColor() != null) {
	        cars = cars.stream()
	                .filter(car -> searchCarDTO.getColor().equalsIgnoreCase(Optional.ofNullable(car.getColor()).orElse("")))
	                .collect(Collectors.toList());
	    }
	    if (searchCarDTO.getTransmission() != null) {
	        cars = cars.stream()
	                .filter(car -> searchCarDTO.getTransmission().equalsIgnoreCase(Optional.ofNullable(car.getTransmission()).orElse("")))
	                .collect(Collectors.toList());
	    }

	    return cars.stream().map(Car::getSearchCarDTO).collect(Collectors.toList());
	}

//	@Override
//	public List<CarDTO> getMyCars(Long userId) {
//		return carRepository.findAllByUserId(userId).stream().map(Car::getCarDTO).collect(Collectors.toList());
//	}
//	
//	@Override
//	public List<String> fetchBidStatuses() {
//	    return bidFeignClient.getBidStatuses();
//	}

//	@Override
//	public boolean bidACar(BidFeignClient bidDTO) {
//		Optional<Car> optionalCar = carRepository.findById(bidDTO.getCarId());
//		Optional<User> optionalUser = userRepository.findById(bidDTO.getUserId());
//		if (optionalCar.isPresent() && optionalUser.isPresent()) {
//			Bid bid = new Bid();
//			bid.setUser(optionalUser.get());
//			bid.setCar(optionalCar.get());
//			bid.setPrice(bidDTO.getPrice());
//			bid.setBidStatus(BidStatus.PENDING);
//			bidRepository.save(bid);
//			return true;
//		}
//		return false;
//	}

//	@Override
//	public List<BidFeignClient> getBidsByUserId(Long userId) {
//		return bidRepository.findAllByUserId(userId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<BidFeignClient> getBidsByCarId(Long carId) {
//		return bidRepository.findAllByCarId(carId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
//	}

//	@Override
//	public boolean updateBidStatus(Long bidId, String status) {
//		Optional<Bid> optionalBid=bidRepository.findById(bidId);
//		if (optionalBid.isPresent()) {
//			Bid existingBid=optionalBid.get();
//			if(existingBid.getCar().getSold()) {
//				return false;
//			}
//			if(Objects.equals(status, "Approve")) {
//				existingBid.setBidStatus(BidStatus.APPROVED);
//			}
//			else {
//				existingBid.setBidStatus(BidStatus.REJECTED);
//			}
//			bidRepository.save(existingBid);
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public AnalyticsDTO getAnalytics(Long userId) {
//		AnalyticsDTO analyticsDTO = new AnalyticsDTO();
//		analyticsDTO.setTotalCars(carRepository.countByUserId(userId));
//		analyticsDTO.setSoldCars(carRepository.countByUserIdAndSoldTrue(userId));
//		return analyticsDTO;
//	}
}
