package it.erika.albanese.itineraryplanner.domain.repository;

import it.erika.albanese.itineraryplanner.domain.model.Leg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LegRepository extends JpaRepository<Leg, UUID> {

    //Derived Query Methods in Spring Data JPA Repositories
    Page<Leg> findByItineraries_Id(UUID itineraryId, Pageable pageable);

}
