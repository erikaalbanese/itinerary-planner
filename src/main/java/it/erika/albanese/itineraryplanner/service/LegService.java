package it.erika.albanese.itineraryplanner.service;

import it.erika.albanese.itineraryplanner.domain.model.Leg;
import it.erika.albanese.itineraryplanner.domain.repository.LegRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LegService {

    final private LegRepository legRepository;

    public Optional<Leg> findLegById(UUID id){
        return legRepository.findById(id);
    }

    public Page<Leg> getAllLegsPaginated(Pageable pageable) {
        return legRepository.findAll(pageable);
    }

}
