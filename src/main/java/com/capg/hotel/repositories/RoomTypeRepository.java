package com.capg.hotel.repositories;

import com.capg.hotel.entities.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;
import java.util.List;

@RepositoryRestResource(
        collectionResourceRel = "roomTypes",
        path = "roomTypes"
)
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    @RestResource(path = "byTypeName", rel = "byTypeName")
    Page<RoomType> findByTypeNameContainingIgnoreCase(
            @Param("typeName") String typeName,
            Pageable pageable
    );

    @RestResource(path = "byMaxOccupancy", rel = "byMaxOccupancy")
    List<RoomType> findByMaxOccupancyGreaterThanEqual(
            @Param("occupancy") Integer occupancy
    );

    @RestResource(path = "byPriceRange", rel = "byPriceRange")
    List<RoomType> findByPricePerNightBetween(
            @Param("min") BigDecimal min,
            @Param("max") BigDecimal max
    );
}
