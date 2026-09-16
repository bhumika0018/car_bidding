package com.carsale.bid.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carsale.bid.dto.BidDTO;
import com.carsale.bid.entity.Bid;
import com.carsale.bid.enums.BidStatus;
import com.carsale.bid.repository.BidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidService {
	
	@Autowired
	private final BidRepository bidRepository;

	public BidDTO placeBid(BidDTO bidDTO) {
        Bid bid = new Bid();
        bid.setPrice(bidDTO.getPrice());
        bid.setCarId(bidDTO.getCarId());
        bid.setUserId(bidDTO.getUserId());
        bid.setBidStatus(BidStatus.PENDING);
        bidRepository.save(bid);
        return bid.getBidDTO();
    }
	
	public List<BidDTO> getBidsByUser(Long userId) {
        return bidRepository.findByUserId(userId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
    }

	
	
    public List<BidDTO> getAllBids() {
        return bidRepository.findAll().stream().map(Bid::getBidDTO).collect(Collectors.toList());
    }

    public List<BidDTO> getBidsByCar(Long carId) {
        return bidRepository.findByCarId(carId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
    }

    

    public BidDTO getHighestBidForCar(Long carId) {
        Optional<Bid> highestBid = bidRepository.findTopByCarIdOrderByPriceDesc(carId);
        return highestBid.map(Bid::getBidDTO).orElse(null);
    }

    public Long getBidCountForCar(Long carId) {
        return bidRepository.countByCarId(carId);
    }

    public BidDTO updateBidStatus(Long bidId, String status) {
        Optional<Bid> bidOptional = bidRepository.findById(bidId);
        if (bidOptional.isPresent()) {
            Bid bid = bidOptional.get();
            bid.setBidStatus(BidStatus.valueOf(status.toUpperCase()));
            bidRepository.save(bid);
            return bid.getBidDTO();
        }
        return null;
    }

    public void deleteBid(Long bidId) {
        bidRepository.deleteById(bidId);
    }
}
