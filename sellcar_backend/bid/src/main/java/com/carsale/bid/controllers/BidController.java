package com.carsale.bid.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carsale.bid.dto.BidDTO;
import com.carsale.bid.services.BidService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/api/bid")
public class BidController {
	private final BidService bidService;

	@PostMapping("/place")
    public ResponseEntity<BidDTO> placeBid(@RequestBody BidDTO bidDTO) {
        return ResponseEntity.ok(bidService.placeBid(bidDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BidDTO>> getAllBids() {
        return ResponseEntity.ok(bidService.getAllBids());
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<BidDTO>> getBidsByCar(@PathVariable Long carId) {
        return ResponseEntity.ok(bidService.getBidsByCar(carId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BidDTO>> getBidsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bidService.getBidsByUser(userId));
    }

    @GetMapping("/car/{carId}/highest")
    public ResponseEntity<BidDTO> getHighestBidForCar(@PathVariable Long carId) {
        return ResponseEntity.ok(bidService.getHighestBidForCar(carId));
    }

    @GetMapping("/car/{carId}/count")
    public ResponseEntity<Long> getBidCountForCar(@PathVariable Long carId) {
        return ResponseEntity.ok(bidService.getBidCountForCar(carId));
    }

    @PutMapping("/update/{bidId}")
    public ResponseEntity<BidDTO> updateBidStatus(@PathVariable Long bidId, @RequestBody Map<String, String> status) {

    	String singleStatus = status.get("status");
        return ResponseEntity.ok(bidService.updateBidStatus(bidId, singleStatus));
    }

    @DeleteMapping("/delete/{bidId}")
    public ResponseEntity<String> deleteBid(@PathVariable Long bidId) {
        bidService.deleteBid(bidId);	
        return ResponseEntity.ok("Bid deleted successfully");
    }
    
//	@GetMapping("/my-cars/{userId}")
//	public ResponseEntity<List<BidDTO>> getMyCars(@PathVariable Long userId){
//		return ResponseEntity.ok(customerService.getMyCars(userId));
//	}
}
