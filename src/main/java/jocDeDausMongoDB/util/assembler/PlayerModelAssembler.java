package jocDeDausMongoDB.util.assembler;

import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.collection.PlayerCollection;
import jocDeDausMongoDB.controller.PlayerControllerMongoDB;
import jocDeDausMongoDB.dto.CrapsRollModel;
import jocDeDausMongoDB.dto.PlayerModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
public class PlayerModelAssembler extends RepresentationModelAssemblerSupport<PlayerCollection, PlayerModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PlayerModelAssembler() {
        super(PlayerControllerMongoDB.class, PlayerModel.class);
    }

    @Override
    public PlayerModel toModel(PlayerCollection playerCollection) {

        PlayerModel playerModel = instantiateModel(playerCollection);

        playerModel.add(linkTo(
                methodOn(PlayerControllerMongoDB.class)
                        .one(playerCollection.getIdPlayer()))
                .withSelfRel());

        playerModel = modelMapperResource(playerCollection);

        return playerModel;
    }

    @Override
    public CollectionModel<PlayerModel> toCollectionModel(Iterable<? extends PlayerCollection> collection)
    {
        CollectionModel<PlayerModel> playerModels = super.toCollectionModel(collection);

        playerModels.add(linkTo(methodOn(PlayerControllerMongoDB.class)
                .allPlayers()).withSelfRel());

        return playerModels;
    }

    public PlayerModel modelMapperResource(PlayerCollection playerCollection){
        PlayerModel playerModel = modelMapper.map(playerCollection, PlayerModel.class);
        playerModel.setCrapsRolls(toCrapsRollModel(playerCollection.getCrapsRolls()));
        return playerModel;
    }


    private List<CrapsRollModel> toCrapsRollModel(List<CrapsRollCollection> crapsRolls) {
        if (crapsRolls.isEmpty())
            return Collections.emptyList();

        return crapsRolls.stream()
                .map(crapsRoll -> CrapsRollModel.builder()
                        .idCrapsRoll(crapsRoll.getIdCrapsRoll())
                        .idPlayer(crapsRoll.getIdPlayer())
                        .crapOne(crapsRoll.getCrapOne())
                        .crapTwo(crapsRoll.getCrapTwo())
                        .rollResult(crapsRoll.getRollResult())
                        .build()
                        .add(linkTo(
                                methodOn(PlayerControllerMongoDB.class)
                                        .allCrapsRollsByPlayer(crapsRoll.getIdPlayer()))
                                .withSelfRel()))
                .collect(Collectors.toList());
    }




}
