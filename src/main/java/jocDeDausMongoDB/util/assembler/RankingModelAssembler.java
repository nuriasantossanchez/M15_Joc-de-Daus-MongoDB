package jocDeDausMongoDB.util.assembler;

import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.collection.PlayerCollection;
import jocDeDausMongoDB.controller.PlayerControllerMongoDB;
import jocDeDausMongoDB.resource.CrapsRollModel;
import jocDeDausMongoDB.resource.PlayerModel;
import jocDeDausMongoDB.resource.RankingModel;
import jocDeDausMongoDB.collection.Ranking;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RankingModelAssembler extends RepresentationModelAssemblerSupport<Ranking, RankingModel> {

    @Autowired
    private ModelMapper modelMapper;

    public RankingModelAssembler() {
        super(PlayerControllerMongoDB.class, RankingModel.class);
    }

    @Override
    public RankingModel toModel(Ranking ranking) {

        RankingModel rankingModel = instantiateModel(ranking);

        rankingModel.add(linkTo(
                methodOn(PlayerControllerMongoDB.class)
                        .one("1"))
                .withSelfRel());

        rankingModel = modelMapperResource(ranking);


        return rankingModel;
    }

    @Override
    public CollectionModel<RankingModel> toCollectionModel(Iterable<? extends Ranking> collection)
    {
        CollectionModel<RankingModel> rankingModels = super.toCollectionModel(collection);

        rankingModels.add(linkTo(methodOn(PlayerControllerMongoDB.class)
                .allPlayers()).withSelfRel());

        return rankingModels;
    }

    public RankingModel modelMapperResource(Ranking ranking){
        RankingModel rankingModel = modelMapper.map(ranking, RankingModel.class);
        return rankingModel;
    }

}
