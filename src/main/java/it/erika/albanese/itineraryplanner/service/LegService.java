package it.erika.albanese.itineraryplanner.service;

import it.erika.albanese.itineraryplanner.domain.model.Leg;
import it.erika.albanese.itineraryplanner.domain.repository.ItineraryRepository;
import it.erika.albanese.itineraryplanner.domain.repository.LegRepository;
import it.erika.albanese.itineraryplanner.exception.InvalidItineraryException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static it.erika.albanese.itineraryplanner.utils.ErrorMessages.ITINERARY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LegService {

    final private LegRepository legRepository;
    final private ItineraryRepository itineraryRepository;

    public Optional<Leg> findLegById(UUID id){
        return legRepository.findById(id);
    }

    public Page<Leg> getAllLegsPaginated(Pageable pageable) {
        return legRepository.findAll(pageable);
    }

    public Page<Leg> findByItineraryId(UUID itineraryId, Pageable pageable){
        if(itineraryRepository.existsById(itineraryId)){
            return legRepository.findByItineraries_Id(itineraryId, pageable);
        } else{
            throw new InvalidItineraryException(ITINERARY_NOT_FOUND);
        }

    }
}
