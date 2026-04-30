package com.capg.hotel.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.capg.hotel.entities.Amenity;
import com.capg.hotel.projections.AmenityProjection;

@RepositoryRestResource(
        path = "amenities",
        collectionResourceRel = "amenities",
        excerptProjection = AmenityProjection.class
)
public interface AmenityRepository extends JpaRepository<Amenity,Integer> {
	@RestResource(exported=false)
	void deleteById(int id);
	
	@RestResource(exported=false)
	void delete(Amenity amenity);
	
	Page<Amenity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}