package it.erika.albanese.itineraryplanner.assembler;

import it.erika.albanese.itineraryplanner.domain.model.Itinerary;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class ItineraryModelAssembler implements RepresentationModelAssembler<Itinerary, EntityModel<Itinerary>> {


    @Override
    public EntityModel<Itinerary> toModel(Itinerary entity) {
        return EntityModel.of(entity);
    }

    @Override
    public CollectionModel<EntityModel<Itinerary>> toCollectionModel(Iterable<? extends Itinerary> entities) {

        List<EntityModel<Itinerary>> itinerariesList = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel).collect(Collectors.toList());

        return CollectionModel.of(itinerariesList);
    }
}
