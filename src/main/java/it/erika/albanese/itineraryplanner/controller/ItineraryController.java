package it.erika.albanese.itineraryplanner.controller;

import it.erika.albanese.itineraryplanner.assembler.ItineraryModelAssembler;
import it.erika.albanese.itineraryplanner.assembler.LegModelAssembler;
import it.erika.albanese.itineraryplanner.domain.model.Itinerary;
import it.erika.albanese.itineraryplanner.domain.model.Leg;
import it.erika.albanese.itineraryplanner.dto.CreateItineraryDto;
import it.erika.albanese.itineraryplanner.dto.UpdateItineraryDto;
import it.erika.albanese.itineraryplanner.exception.InvalidItineraryException;
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

import java.util.Optional;
import java.util.UUID;

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
        return ResponseEntity.ok(itineraryAssembler.toModel(itinerary));
    }

    // 3.Itinerary Point Indication: Users can indicate where they are on the itinerary, for example by specifying the current stop or location.
    // 5.Itinerary Modification: the user can modify the itinerary even while it is in progress.
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Itinerary>> editItinerary(@PathVariable UUID id, @Valid @RequestBody UpdateItineraryDto dto) {
        Itinerary itinerary = itineraryService.editItinerary(id, dto);
        return ResponseEntity.ok(itineraryAssembler.toModel(itinerary));
    }

    // 4.Itinerary Visualization: Users must be able to view which itineraries are currently being created and which are next in line to be processed, along with the estimated completion time.
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Itinerary>>>
    getItineraries(@RequestParam(required = false) ItineraryStatus status,
                   @RequestParam(required = false, defaultValue = "asc") String sort,
                   @RequestParam(defaultValue = "0") int page,
                   @RequestParam(defaultValue = "10") int size) {

        Sort sorting = sort.equalsIgnoreCase("asc") ? Sort.by("estimatedTime").ascending() :
                Sort.by("estimatedTime").descending();

        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<Itinerary> itinerariesPage = itineraryService.getItineraries(status, pageable);

        PagedModel<EntityModel<Itinerary>> pagedModel = itineraryPagedResourcesAssembler.toModel(itinerariesPage, itineraryAssembler);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Itinerary>>
    getItinerary(@PathVariable UUID id) {
        Optional<Itinerary> itineraryOptional = itineraryService.findItineraryById(id);

        if(itineraryOptional.isPresent()){
            Itinerary itinerary = itineraryOptional.get();

            EntityModel<Itinerary> itineraryModel = itineraryAssembler.toModel(itinerary);

            return ResponseEntity.ok(itineraryModel);
        } else {
            throw new InvalidItineraryException("Itinerary not found");
        }
    }

    @GetMapping("/{id}/legs")
    public ResponseEntity<PagedModel<EntityModel<Leg>>>
    getItineraryLegs(@PathVariable UUID id,
                   @RequestParam(required = false, defaultValue = "asc") String sort,
                   @RequestParam(defaultValue = "0") int page,
                   @RequestParam(defaultValue = "10") int size) {
        Sort sorting = sort.equalsIgnoreCase("asc") ? Sort.by("name").ascending() :
                Sort.by("name").descending();

        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Leg> legsPage = legService.findByItineraryId(id, pageable);

        PagedModel<EntityModel<Leg>> pagedModel = legPagedResourcesAssembler.toModel(legsPage, legAssembler);

        return ResponseEntity.ok(pagedModel);
    }
}


