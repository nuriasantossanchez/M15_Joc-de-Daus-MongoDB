package jocDeDausMongoDB.util.assembler;

import jocDeDausMongoDB.controller.PlayerControllerMongoDB;
import jocDeDausMongoDB.dto.CrapsRollModel;
import jocDeDausMongoDB.collection.CrapsRollCollection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Clase de la capa de Utilidades
 *
 * Implemente la interfaz RepresentationModelAssembler
 * (pertenece al modulo de Spring HATEOAS, org.springframework.hateoas.server)
 *
 * Convierte un objeto de dominio en un RepresentationModel, que es una clase base
 * para que los DTO recopilen enlaces, un EntityModel simple que envuelve un objeto
 * de dominio y le agrega enlaces.
 *
 */

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
