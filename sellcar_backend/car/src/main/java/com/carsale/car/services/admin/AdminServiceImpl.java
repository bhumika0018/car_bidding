package com.carsale.car.services.admin;

import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carsale.car.dto.CarDTO;
import com.carsale.car.dto.SearchCarDTO;
import com.carsale.car.entity.Car;
import com.carsale.car.repository.CarRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	@Autowired
	private final CarRepository carRepository;

	@Override
	public boolean createCar(CarDTO carDTO) throws IOException {
		Car car = new Car();
		car.setName(carDTO.getName());
		car.setBrand(carDTO.getBrand());
		car.setType(carDTO.getType());
		car.setTransmission(carDTO.getTransmission());
		car.setColor(carDTO.getColor());
		car.setYear(carDTO.getYear());
		car.setSold(false);
		car.setDescription(carDTO.getDescription());
		car.setPrice(carDTO.getPrice());

		// Convert image to byte array if not null
		if (carDTO.getImg() != null) {
			car.setImg(carDTO.getImg().getBytes());
		}

		carRepository.save(car);
		return true;
	}

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
	public void deleteCar(Long id) {
		carRepository.deleteById(id);
	}

	@Override
	public boolean updateCar(Long carId, CarDTO carDTO) throws IOException {
		Optional<Car> optionalCar = carRepository.findById(carId);
		if (optionalCar.isPresent()) {
			Car car = optionalCar.get();
			car.setName(carDTO.getName());
			car.setBrand(carDTO.getBrand());
			car.setType(carDTO.getType());
			car.setTransmission(carDTO.getTransmission());
			car.setColor(carDTO.getColor());
			car.setYear(carDTO.getYear());
			car.setPrice(carDTO.getPrice());
			car.setDescription(carDTO.getDescription());

			// If an image is provided, update the image as well
			if (carDTO.getImg() != null && !carDTO.getImg().isEmpty()) {
				car.setImg(carDTO.getImg().getBytes());
			}
			carRepository.save(car);
			return true;
		}
		return false;
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
//	public List<BidFeignClient> getBids() {
//		return bidRepository.findAll().stream().map(Bid::getBidDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<BidFeignClient> getBidsByUserId(Long userId) {
//		return bidRepository.findAllByUserId(userId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<BidFeignClient> getBidsByCarId(Long carId) {
//		return bidRepository.findAllByCarId(carId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public boolean updateBidStatus(Long bidId, String status) {
//		Optional<Bid> optionalBid = bidRepository.findById(bidId);
//		if (optionalBid.isPresent()) {
//			Bid existingBid = optionalBid.get();
//			if (Objects.equals(status, "Approve")) {
//				existingBid.setBidStatus(BidStatus.APPROVED);
//			} else {
//				existingBid.setBidStatus(BidStatus.REJECTED);
//			}
//			bidRepository.save(existingBid);
//			return true;
//		}
//		return false;
//	}
}
