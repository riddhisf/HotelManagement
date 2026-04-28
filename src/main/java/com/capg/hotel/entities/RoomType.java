package com.capg.hotel.entities;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "RoomType")

public class RoomType {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "room_type_id")
	 private Integer roomTypeId;

	 @Column(name = "type_name")
	 private String typeName;

	 @Column(name = "description")
	 private String description;

	 @Column(name = "max_occupancy")
	 private Integer maxOccupancy;

	 @Column(name = "price_per_night")
	 private BigDecimal pricePerNight;



}
