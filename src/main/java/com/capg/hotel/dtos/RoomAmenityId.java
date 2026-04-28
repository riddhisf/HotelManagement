package com.capg.hotel.dtos;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class RoomAmenityId implements Serializable {

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "amenity_id")
    private Integer amenityId;


    
}