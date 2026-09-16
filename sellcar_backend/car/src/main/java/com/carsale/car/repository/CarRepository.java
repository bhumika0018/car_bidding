package com.carsale.car.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.carsale.car.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> , JpaSpecificationExecutor<Car>{
	List<Car> findByBrand(String brand);
//	Optional<Car> findByCarId(Long carId);
}

