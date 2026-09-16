package com.carsale.feign;

//import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//import com.carsale.user.dto.BidDTO;


//@FeignClient(name="bid")
public interface BidFeignClient {
	
//	@GetMapping("/api/bid/status")
//    List<String> getBidStatuses();
//	
//	@GetMapping("/car/bids")
//	public ResponseEntity<List<BidDTO>> getBids();
//	
//	@GetMapping("/car/bids/{userId}")
//	public ResponseEntity<List<BidDTO>> getBidsByUserId(@PathVariable Long userId);
//	
//	@GetMapping("/car/{carId}/bids")
//	public ResponseEntity<List<BidDTO>> getBidsByCarId(@PathVariable Long carId);
//	
//	@GetMapping("/car/bid/{bidId}/{status}")
//	public ResponseEntity<?> updateBidStatus(@PathVariable Long bidId, @PathVariable String status);
	
	
}
