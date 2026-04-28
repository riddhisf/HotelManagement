package com.capg.hotel.repositories;

import com.capg.hotel.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "hotels", collectionResourceRel = "hotels")
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @RestResource(path="byLocation", rel = "byLocation")
    Page<Hotel> findByLocationContainingIgnoreCase(@Param("location") String location, Pageable pageable);

    @RestResource(path = "byName", rel = "byName")
    Page<Hotel> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @RestResource(path = "search", rel = "search")
    Page<Hotel> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(@Param("location") String location, @Param("name") String name, Pageable pageable);
}

