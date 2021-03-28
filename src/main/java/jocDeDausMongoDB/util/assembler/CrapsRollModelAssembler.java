package jocDeDausMongoDB.util.assembler;

import jocDeDausMongoDB.collection.PlayerCollection;
import jocDeDausMongoDB.controller.PlayerControllerMongoDB;
import jocDeDausMongoDB.resource.CrapsRollModel;
import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.resource.PlayerModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jocDeDausMongoDB.resource.CrapsRollModel.*;
import static org.springframework.data.mongodb.core.aggregation.MergeOperation.builder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CrapsRollModelAssembler
        extends RepresentationModelAssemblerSupport<CrapsRollCollection, CrapsRollModel> {


    @Autowired
    private ModelMapper modelMapper;

    public CrapsRollModelAssembler() {
        super(PlayerControllerMongoDB.class, CrapsRollModel.class);
    }
    @Override
    public CrapsRollModel toModel(CrapsRollCollection crapsRollCollection) {

        CrapsRollModel crapsRollModel = instantiateModel(crapsRollCollection);

        crapsRollModel.add(linkTo(
                methodOn(PlayerControllerMongoDB.class)
                        .one(crapsRollCollection.getIdPlayer()))
                .withSelfRel());

        crapsRollModel = modelMapperResource(crapsRollCollection);


        return crapsRollModel;
    }

    @Override
    public CollectionModel<CrapsRollModel> toCollectionModel(Iterable<? extends CrapsRollCollection> collection)
    {
        CollectionModel<CrapsRollModel> crapsRollModels = super.toCollectionModel(collection);

        crapsRollModels.add(linkTo(methodOn(PlayerControllerMongoDB.class)
                .allPlayers()).withSelfRel());

        return crapsRollModels;
    }

    public CrapsRollModel modelMapperResource(CrapsRollCollection crapsRollCollection){
        CrapsRollModel crapsRollModel = modelMapper.map(crapsRollCollection, CrapsRollModel.class);
        return crapsRollModel;
    }
}
