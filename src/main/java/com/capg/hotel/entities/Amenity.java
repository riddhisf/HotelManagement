package com.capg.hotel.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Amenity")
public class Amenity {

    @Id
    @Column(name = "amenity_id")
    private Integer amenityId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


}