package com.capg.hotel.entities;


import com.capg.hotel.dtos.RoomAmenityId;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "RoomAmenity")
public class RoomAmenity {

    @EmbeddedId
    private RoomAmenityId id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @MapsId("amenityId")
    @JoinColumn(name = "amenity_id")
    private Amenity amenity;

}