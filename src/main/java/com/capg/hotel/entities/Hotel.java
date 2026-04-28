package com.capg.hotel.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Hotel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {

    @Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Integer hotelId;
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "description")
    private String description;

}