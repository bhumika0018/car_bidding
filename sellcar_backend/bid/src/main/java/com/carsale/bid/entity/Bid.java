package com.carsale.bid.entity;

import com.carsale.bid.dto.BidDTO;
import com.carsale.bid.enums.BidStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bid")
public class Bid {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bidId;
	private Long price;
	private Long carId;
	private Long userId;
	
	@Enumerated(EnumType.STRING)
	private BidStatus bidStatus;

	public BidDTO getBidDTO() {
		BidDTO bidDTO = new BidDTO();
        bidDTO.setBidId(bidId);
        bidDTO.setPrice(price);
        bidDTO.setUserId(userId);
        bidDTO.setCarId(carId);
        bidDTO.setBidStatus(bidStatus);
		return bidDTO;
	}
	

//	@ManyToOne(fetch=FetchType.LAZY,optional=false)
//	@JoinColumn(name="user_id", nullable=false)
//	@OnDelete(action=OnDeleteAction.CASCADE)
//	@JsonIgnore
//	private User user;

//	@ManyToOne(fetch=FetchType.LAZY,optional=false)
//	@JoinColumn(name="car_id", nullable=false)
//	@OnDelete(action=OnDeleteAction.CASCADE)
//	@JsonIgnore
//	private Car car;
}
