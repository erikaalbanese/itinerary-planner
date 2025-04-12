package it.erika.albanese.itineraryplanner.service;

import it.erika.albanese.itineraryplanner.domain.model.Itinerary;
import it.erika.albanese.itineraryplanner.domain.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository repository;

    public Page<Itinerary> getItineraries(Boolean completed, Pageable pageable) {
        if(completed != null ){
            return repository.findByCompleted(completed, pageable);
        }else {
            return repository.findAll(pageable);
        }
    }
}






