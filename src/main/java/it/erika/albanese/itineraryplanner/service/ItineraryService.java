package it.erika.albanese.itineraryplanner.service;

import it.erika.albanese.itineraryplanner.domain.model.Itinerary;
import it.erika.albanese.itineraryplanner.domain.model.Leg;
import it.erika.albanese.itineraryplanner.domain.repository.ItineraryRepository;
import it.erika.albanese.itineraryplanner.domain.repository.LegRepository;
import it.erika.albanese.itineraryplanner.dto.CreateItineraryDto;
import it.erika.albanese.itineraryplanner.dto.UpdateItineraryDto;
import it.erika.albanese.itineraryplanner.exception.InvalidItineraryException;
import it.erika.albanese.itineraryplanner.exception.InvalidLegException;
import it.erika.albanese.itineraryplanner.exception.MaximumItinerariesReachedException;
import it.erika.albanese.itineraryplanner.utils.ItineraryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private static final Long MAX_ITINERARIES = 10L;
    private final ItineraryRepository repository;
    private final LegRepository legRepository;

    public Long countItineraries() {
        return repository.count();
    }

    public boolean existsById(UUID id){
        return repository.existsById(id);
    }

    public Itinerary createItinerary(CreateItineraryDto dto) {
        Long itinerariesNumber = countItineraries();

        if (itinerariesNumber < MAX_ITINERARIES) {
            Itinerary itinerary = new Itinerary();

            itinerary.setPlace(dto.getPlace());
            itinerary.setEstimatedTime(dto.getEstimatedTime());

            Set<Leg> legs = new HashSet<>();

            if(dto.getLegsIds() == null || dto.getLegsIds().isEmpty()){
                itinerary.setStatus(ItineraryStatus.PENDING);
            } else {
                itinerary.setStatus(dto.getStatus());

                dto.getLegsIds().forEach(id -> {
                    Optional<Leg> legOptional = legRepository.findById(id);
                    if (legOptional.isPresent()) {
                        legs.add(legOptional.get());
                    } else {
                        throw new InvalidLegException("Leg Not Found");
                    }
                });
            }

            itinerary.setLegs(legs);

            return repository.save(itinerary);
        } else {
            throw new MaximumItinerariesReachedException(
                    "You can't create more than " + MAX_ITINERARIES + " itineraries");
        }
    }

    public Itinerary editItinerary(UUID id, UpdateItineraryDto dto) {
        Optional<Itinerary> itineraryOptional = repository.findById(id);
        if (itineraryOptional.isPresent()) {
            Itinerary itinerary = itineraryOptional.get();

            itinerary.setPlace(dto.getPlace());
            itinerary.setEstimatedTime(dto.getEstimatedTime());

            if(dto.getLegsIds() == null || dto.getLegsIds().isEmpty()){
                itinerary.setStatus(ItineraryStatus.PENDING);
            } else {
                itinerary.setStatus(dto.getStatus());
            }

            Set<Leg> legs = new HashSet<>();

            dto.getLegsIds().forEach(legId -> {
                Optional<Leg> legOptional = legRepository.findById(legId);
                if (legOptional.isPresent()) {
                    legs.add(legOptional.get());
                } else {
                    throw new InvalidLegException("Leg Not Found");
                }
            });

            itinerary.setLegs(legs);

            return repository.save(itinerary);
        } else {
            throw new InvalidItineraryException("Itinerary Not Found");
        }
    }

    public Page<Itinerary> getItineraries(ItineraryStatus status, Pageable pageable) {
        if (status != null) {
            return repository.findByStatus(status, pageable);
        } else {
            return repository.findAll(pageable);
        }
    }
}






