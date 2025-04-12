package it.erika.albanese.itineraryplanner.domain.repository;

import it.erika.albanese.itineraryplanner.domain.model.Itinerary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, UUID> {

    Page<Itinerary> findByCompleted(Boolean completed, Pageable pageable);

}
