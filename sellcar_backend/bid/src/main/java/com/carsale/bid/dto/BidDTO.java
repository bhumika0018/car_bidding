package com.carsale.bid.dto;

import com.carsale.bid.enums.BidStatus;

import lombok.Data;

@Data
public class BidDTO {
	private Long bidId;
	private Long price;
	private Long userId;
	private Long carId;
	private BidStatus bidStatus;
}
