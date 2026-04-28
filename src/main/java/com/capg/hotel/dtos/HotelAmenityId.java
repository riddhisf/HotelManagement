package com.capg.hotel.dtos;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class HotelAmenityId implements Serializable {

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "amenity_id")
    private Integer amenityId;
}