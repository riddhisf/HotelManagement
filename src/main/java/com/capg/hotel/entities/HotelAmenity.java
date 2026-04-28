package com.capg.hotel.entities;

import com.capg.hotel.dtos.HotelAmenityId;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "HotelAmenity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelAmenity {

    @EmbeddedId
    private HotelAmenityId id;

    @ManyToOne
    @MapsId("hotelId")
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @MapsId("amenityId")
    @JoinColumn(name = "amenity_id")
    private Amenity amenity;

    
}