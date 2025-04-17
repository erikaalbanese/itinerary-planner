package it.erika.albanese.itineraryplanner.assembler;

import it.erika.albanese.itineraryplanner.controller.ItineraryController;
import it.erika.albanese.itineraryplanner.domain.model.Itinerary;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class ItineraryModelAssembler implements RepresentationModelAssembler<Itinerary, EntityModel<Itinerary>> {


    @Override
    public EntityModel<Itinerary> toModel(Itinerary entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(ItineraryController.class).getItinerary(entity.getId())).withSelfRel(),
                linkTo(methodOn(ItineraryController.class).getItineraries(null, "asc", 0, 10))
                        .withRel("itineraries"),
                linkTo(methodOn(ItineraryController.class).getItineraryLegs(entity.getId(),
                        "asc", 0, 10)).withRel("legs")
        );
    }

    @Override
    public CollectionModel<EntityModel<Itinerary>> toCollectionModel(Iterable<? extends Itinerary> entities) {

        List<EntityModel<Itinerary>> itinerariesList = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel).collect(Collectors.toList());

        return CollectionModel.of(itinerariesList);
    }
}
