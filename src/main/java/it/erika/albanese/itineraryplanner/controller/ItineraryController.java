package it.erika.albanese.itineraryplanner.controller;

import it.erika.albanese.itineraryplanner.assembler.ItineraryModelAssembler;
import it.erika.albanese.itineraryplanner.assembler.LegModelAssembler;
import it.erika.albanese.itineraryplanner.domain.model.Itinerary;
import it.erika.albanese.itineraryplanner.domain.model.Leg;
import it.erika.albanese.itineraryplanner.dto.CreateItineraryDto;
import it.erika.albanese.itineraryplanner.dto.UpdateItineraryDto;
import it.erika.albanese.itineraryplanner.exception.InvalidItineraryException;
import it.erika.albanese.itineraryplanner.exception.InvalidSortException;
import it.erika.albanese.itineraryplanner.service.ItineraryService;
import it.erika.albanese.itineraryplanner.service.LegService;
import it.erika.albanese.itineraryplanner.utils.ItineraryStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static it.erika.albanese.itineraryplanner.utils.ErrorMessages.INVALID_SORT;
import static it.erika.albanese.itineraryplanner.utils.ErrorMessages.ITINERARY_NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;
    private final LegService legService;
    private final ItineraryModelAssembler itineraryAssembler;
    private final LegModelAssembler legAssembler;
    private final PagedResourcesAssembler<Itinerary> itineraryPagedResourcesAssembler;
    private final PagedResourcesAssembler<Leg> legPagedResourcesAssembler;

    //1.Itinerary Creation Without Registration: Users can create travel itineraries without the need for registration.
    //2.Itinerary Limitation: The system must ensure that no more itineraries are accepted than it can handle concurrently.
    @PostMapping
    public ResponseEntity<EntityModel<Itinerary>> createItinerary(@Valid @RequestBody CreateItineraryDto dto) {
        Itinerary itinerary = itineraryService.createItinerary(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(itinerary.getId())
                .toUri();

        // Returns 201 creates instead of 200 OK
        return ResponseEntity.created(location).body(itineraryAssembler.toModel(itinerary));
    }

    // 3.Itinerary Point Indication: Users can indicate where they are on the itinerary, for example by specifying the current leg or place.
    // 5.Itinerary Modification: the user can modify the itinerary even while it is in progress.
    @PutMapping("/{id}")
    public EntityModel<Itinerary> editItinerary(@PathVariable UUID id, @Valid @RequestBody UpdateItineraryDto dto) {
        Itinerary itinerary = itineraryService.editItinerary(id, dto);
        return itineraryAssembler.toModel(itinerary);
    }

    // 4.Itinerary Visualization: Users must be able to view which itineraries are currently being created
    // and which are next in line to be processed, along with the estimated completion time.
    @GetMapping
    public PagedModel<EntityModel<Itinerary>>
    getItineraries(@RequestParam(required = false) ItineraryStatus status,
                   @RequestParam(required = false, defaultValue = "asc") String sort,
                   @RequestParam(defaultValue = "0") int page,
                   @RequestParam(defaultValue = "10") int size) {

        Sort sorting;
        if("asc".equalsIgnoreCase(sort)){
            sorting = Sort.by("estimatedTime").ascending();
        } else if("desc".equalsIgnoreCase(sort)){
            sorting = Sort.by("estimatedTime").descending();
        } else {
            throw new InvalidSortException(INVALID_SORT);
        }

        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<Itinerary> itinerariesPage = itineraryService.getItineraries(status, pageable);

        return itineraryPagedResourcesAssembler.toModel(itinerariesPage, itineraryAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<Itinerary>
    getItinerary(@PathVariable UUID id) {
        Optional<Itinerary> itineraryOptional = itineraryService.findItineraryById(id);

        if (itineraryOptional.isPresent()) {
            Itinerary itinerary = itineraryOptional.get();

            return itineraryAssembler.toModel(itinerary);
        } else {
            throw new InvalidItineraryException(ITINERARY_NOT_FOUND);
        }
    }

    @GetMapping("/{id}/legs")
    public PagedModel<EntityModel<Leg>>
    getItineraryLegs(@PathVariable UUID id,
                     @RequestParam(required = false, defaultValue = "asc") String sort,
                     @RequestParam(defaultValue = "0") int page,
                     @RequestParam(defaultValue = "10") int size) {

        Sort sorting;
        if("asc".equalsIgnoreCase(sort)){
            sorting = Sort.by("estimatedTime").ascending();
        } else if("desc".equalsIgnoreCase(sort)){
            sorting = Sort.by("estimatedTime").descending();
        } else {
            throw new InvalidSortException(INVALID_SORT);
        }

        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Leg> legsPage = legService.findByItineraryId(id, pageable);

        return legPagedResourcesAssembler.toModel(legsPage, legAssembler);
    }
}


