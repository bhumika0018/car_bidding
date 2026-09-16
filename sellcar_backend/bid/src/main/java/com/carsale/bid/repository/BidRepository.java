package com.carsale.bid.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carsale.bid.entity.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
	List<Bid> findByCarId(Long carId);

    List<Bid> findByUserId(Long userId);

    Optional<Bid> findTopByCarIdOrderByPriceDesc(Long carId);

    Long countByCarId(Long carId);
}
