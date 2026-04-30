package com.capg.hotel.projections;

import org.springframework.data.rest.core.config.Projection;

import com.capg.hotel.entities.Amenity;

@Projection(name = "amenitySummary", types = { Amenity.class })
public interface AmenityProjection {

    Integer getAmenityId();
    String getName();
    String getDescription();
}