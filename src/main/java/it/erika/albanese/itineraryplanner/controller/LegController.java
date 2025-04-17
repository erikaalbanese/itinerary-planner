package it.erika.albanese.itineraryplanner.controller;

import it.erika.albanese.itineraryplanner.assembler.LegModelAssembler;
import it.erika.albanese.itineraryplanner.domain.model.Leg;
import it.erika.albanese.itineraryplanner.service.LegService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/legs")
public class LegController {

    private final LegService legService;
    private final LegModelAssembler legAssembler;
    private final PagedResourcesAssembler<Leg> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<EntityModel<Leg>> getLegs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "asc") String sort) {
        Sort sorting = sort.equalsIgnoreCase("asc") ? Sort.by("name").ascending() :
                Sort.by("name").descending();

        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<Leg> legs = legService.getAllLegsPaginated(pageable);

        return pagedResourcesAssembler.toModel(legs, legAssembler);
    }
}
