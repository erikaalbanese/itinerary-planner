package it.erika.albanese.itineraryplanner.service;

import it.erika.albanese.itineraryplanner.domain.model.Itinerary;
import it.erika.albanese.itineraryplanner.domain.model.Leg;
import it.erika.albanese.itineraryplanner.domain.repository.ItineraryRepository;
import it.erika.albanese.itineraryplanner.dto.CreateItineraryDto;
import it.erika.albanese.itineraryplanner.exception.InvalidLegException;
import it.erika.albanese.itineraryplanner.exception.MaximumItinerariesReachedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository repository;
    private final LegService legService;
    private static final Long MAX_ITINERARIES = 1L;

    public Long countItineraries(){
        return repository.count();
    }

    public Itinerary createItinerary(CreateItineraryDto dto){
        Long itinerariesNumber = countItineraries();

        if(itinerariesNumber < MAX_ITINERARIES) {
            Itinerary itinerary = new Itinerary();

            itinerary.setPlace(dto.getPlace());
            itinerary.setEstimatedTime(dto.getEstimatedTime());
            itinerary.setCompleted(dto.isCompleted());

            dto.getLegsIds().forEach(id -> {
                Optional<Leg> legOptional = legService.findLegById(id);
                if (legOptional.isPresent()) {
                    Set<Leg> legs = itinerary.getLegs();
                    legs.add(legOptional.get());
                } else {
                    throw new InvalidLegException("Leg Not Found");
                }
            });

            return repository.save(itinerary);
        } else {
            throw new MaximumItinerariesReachedException(
                    "You can't create more than " + MAX_ITINERARIES + " itineraries");
        }
    }

    public Page<Itinerary> getItineraries(Boolean completed, Pageable pageable) {
        if(completed != null ){
            return repository.findByCompleted(completed, pageable);
        }else {
            return repository.findAll(pageable);
        }
    }
}






