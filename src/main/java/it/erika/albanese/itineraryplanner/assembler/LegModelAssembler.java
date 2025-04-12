package it.erika.albanese.itineraryplanner.assembler;

import it.erika.albanese.itineraryplanner.domain.model.Leg;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class LegModelAssembler implements RepresentationModelAssembler<Leg, EntityModel<Leg>> {


    @Override
    public EntityModel<Leg> toModel(Leg entity) {
        return EntityModel.of(entity);
    }

    @Override
    public CollectionModel<EntityModel<Leg>> toCollectionModel(Iterable<? extends Leg> entities) {
        List<EntityModel<Leg>> legsList = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel).collect(Collectors.toList());

        return CollectionModel.of(legsList);
    }

}
