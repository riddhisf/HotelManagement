package com.capg.hotel.repositories;

import com.capg.hotel.entities.Amenity;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AmenityRepositoryTest {

    @Autowired
    private AmenityRepository amenityRepository;
<<<<<<< HEAD

    // =======================================================
    // Helper
    // =======================================================

    private Amenity save(String name, String desc) {
        Amenity a = new Amenity(null, name, desc);
        return amenityRepository.saveAndFlush(a);
    }

    // =======================================================
    // CORRECT SCENARIOS
    // =======================================================

    @Test
    void testSaveAmenity_valid() {
        Amenity saved = save("Wi-Fi", "High-speed internet");

        assertNotNull(saved);
        assertNotNull(saved.getAmenityId());
        assertEquals("Wi-Fi", saved.getName());
    }

    @Test
    void testSaveAmenity_persistsFields() {
        Amenity saved = save("Gym", "Fitness center");

        Optional<Amenity> found =
                amenityRepository.findById(saved.getAmenityId());

        assertTrue(found.isPresent());
        assertEquals("Gym", found.get().getName());
        assertEquals("Fitness center", found.get().getDescription());
    }

    @Test
    void testFindByNameContainingIgnoreCase_singleMatch() {
        save("Swimming Pool", "Outdoor pool");

        var page = amenityRepository
                .findByNameContainingIgnoreCase("pool", PageRequest.of(0, 5));

        assertEquals(1, page.getContent().size());
        assertEquals("Swimming Pool", page.getContent().get(0).getName());
    }

    @Test
    void testUpdateAmenity_valid() {
        Amenity saved = save("Old Name", "Old Description");

        saved.setName("New Name");
        saved.setDescription("New Description");

        amenityRepository.saveAndFlush(saved);

        Amenity updated =
                amenityRepository.findById(saved.getAmenityId()).orElseThrow();

        assertEquals("New Name", updated.getName());
        assertEquals("New Description", updated.getDescription());
    }

    @Test
    void testCountAmenities_valid() {
        save("AA", "Valid Desc AAAAA");
        save("BB", "Valid Desc BBBBB");

        long count = amenityRepository.count();

        assertEquals(2, count);
    }

    // =======================================================
    // INCORRECT SCENARIOS
    // =======================================================

//    @Test
//    void testFindByName_notFound() {
//        Optional<Amenity> found =
//                amenityRepository.findByName("Unknown");
//
//        assertTrue(found.isEmpty());
//    }

//    @Test
//    void testFindByName_caseSensitive() {
//        save("Tennis Court", "Outdoor court facility");
//
//        Optional<Amenity> found =
//                amenityRepository.findByName("tennis court");
//
//        assertTrue(found.isEmpty());
//    }

    @Test
    void testUpdateDoesNotCreateNewRecord() {
        Amenity saved = save("Parking", "Secure parking");

        long countBefore = amenityRepository.count();

        saved.setName("Updated Parking");

        amenityRepository.saveAndFlush(saved);

        long countAfter = amenityRepository.count();

        assertEquals(countBefore, countAfter);
    }

    // =======================================================
    // INVALID SCENARIOS
    // =======================================================

    @Test
    void testSaveAmenity_blankName_shouldFail() {
        Amenity a = new Amenity(
                null,
                "",
                "Valid description here"
        );

        assertThrows(
                ConstraintViolationException.class,
                () -> amenityRepository.saveAndFlush(a)
        );
    }

    @Test
    void testSaveAmenity_nullName_shouldFail() {
        Amenity a = new Amenity(
                null,
                null,
                "Valid description here"
        );

        assertThrows(
                ConstraintViolationException.class,
                () -> amenityRepository.saveAndFlush(a)
        );
    }

    @Test
    void testSaveAmenity_shortDescription_shouldFail() {
        Amenity a = new Amenity(
                null,
                "Valid Name",
                "abc"
        );

        assertThrows(
                ConstraintViolationException.class,
                () -> amenityRepository.saveAndFlush(a)
        );
    }

    @Test
    void testUpdateAmenity_blankName_shouldFail() {
        Amenity saved = save("Valid Name", "Valid Description");

        saved.setName("");

        assertThrows(
                ConstraintViolationException.class,
                () -> amenityRepository.saveAndFlush(saved)
        );
    }

    @Test
    void testUpdateAmenity_nullName_shouldFail() {
        Amenity saved = save("Valid Name", "Valid Description");

        saved.setName(null);

        assertThrows(
                ConstraintViolationException.class,
                () -> amenityRepository.saveAndFlush(saved)
        );
    }

    @Test
    void testSaveAmenity_nullEntity_shouldFail() {
        assertThrows(
                Exception.class,
                () -> amenityRepository.saveAndFlush(null)
        );
    }
=======
>>>>>>> 7b5957738d34b7fd1fdd99412f1eff4831574c37
    
    //read tests
    @Test
    void shouldFindAmenityById() {
        Amenity a = new Amenity(null, "Temp", "Valid description");
        a = amenityRepository.saveAndFlush(a);

        Optional<Amenity> found = amenityRepository.findById(a.getAmenityId());

        assertTrue(found.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenIdNotFound() {
        Optional<Amenity> found = amenityRepository.findById(-999);

        assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindByNameContainingIgnoreCase() {
        var page = amenityRepository
                .findByNameContainingIgnoreCase("pool", PageRequest.of(0, 10));

        assertFalse(page.isEmpty());
        assertTrue(
            page.getContent().stream()
                .allMatch(a -> a.getName().toLowerCase().contains("pool"))
        );
    }

    @Test
    void shouldBeCaseInsensitiveSearch() {
        var page = amenityRepository
                .findByNameContainingIgnoreCase("POOL", PageRequest.of(0, 10));

        assertFalse(page.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenNoMatchFound() {
        var page = amenityRepository
                .findByNameContainingIgnoreCase("xyz_not_exist", PageRequest.of(0, 10));

        assertTrue(page.isEmpty());
    }

    //pagination tests
    @Test
    void shouldReturnLimitedPageSize() {
        var page = amenityRepository.findAll(PageRequest.of(0, 5));

        assertEquals(5, page.getContent().size());
    }

    @Test
    void shouldReturnDifferentResultsForDifferentPages() {
        var page1 = amenityRepository.findAll(PageRequest.of(0, 5));
        var page2 = amenityRepository.findAll(PageRequest.of(1, 5));

        assertNotEquals(
            page1.getContent().get(0).getAmenityId(),
            page2.getContent().get(0).getAmenityId()
        );
    }

    //update and add tests
    @Test
    void shouldSaveAmenity() {
        long before = amenityRepository.count();

        Amenity a = new Amenity(null, "Test Amenity", "Valid description here");
        Amenity saved = amenityRepository.saveAndFlush(a);

        assertNotNull(saved.getAmenityId());
        assertEquals(before + 1, amenityRepository.count());
    }

    @Test
    void shouldUpdateAmenity() {
        Amenity a = new Amenity(null, "Temp Name", "Valid description");
        a = amenityRepository.saveAndFlush(a);

        a.setName("Updated Name");
        a.setDescription("Updated description");

        amenityRepository.saveAndFlush(a);

        Amenity updated = amenityRepository.findById(a.getAmenityId())
                .orElseThrow();

        assertEquals("Updated Name", updated.getName());
    }

    @Test
    void shouldNotCreateNewRecordOnUpdate() {
        Amenity a = new Amenity(null, "Temp", "Valid description");
        a = amenityRepository.saveAndFlush(a);

        long before = amenityRepository.count();

        a.setName("Updated Temp");
        amenityRepository.saveAndFlush(a);

        long after = amenityRepository.count();

        assertEquals(before, after);
    }

    //invalid cases
    @Test
    void shouldFailWhenNameInvalid() {
        Amenity a = new Amenity(null, "", "Valid description");

        assertThrows(ConstraintViolationException.class,
                () -> amenityRepository.saveAndFlush(a));
    }

    @Test
    void shouldFailWhenDescriptionTooShort() {
        Amenity a = new Amenity(null, "Valid Name", "abc");

        assertThrows(ConstraintViolationException.class,
                () -> amenityRepository.saveAndFlush(a));
    }

    @Test
    void shouldFailWhenEntityIsNull() {
        assertThrows(Exception.class,
                () -> amenityRepository.saveAndFlush(null));
    }

}