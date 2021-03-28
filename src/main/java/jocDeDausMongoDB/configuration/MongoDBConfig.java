package jocDeDausMongoDB.configuration;

import com.mongodb.client.MongoClients;
import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.collection.GameCollection;
import jocDeDausMongoDB.collection.PlayerCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

/**
 * Clase de la capa de Configuration de Spring
 *
 * Especifica las caracteristicas de configuracion para una BBDD Mongo, como la creacion
 * de instancias de tipo MongoDatabase para un cliente Mongo y las configuraciones de
 * MongoTemplate y MongoOperations.
 *
 * Tambien realiza la carga inicial del repositorio de datos, a partir de ficheros .json
 * almacenados como recursos estaticos
 *
 */
@Configuration
@ComponentScan(basePackages = {"jocDeDausMongoDB"})
@EnableMongoRepositories(basePackages={"jocDeDausMongoDB.repository"})
public class MongoDBConfig {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() throws Exception {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        Resource sourceDataPlayer = new ClassPathResource("db/dataPlayer.json");
        Resource sourceDataCrapsRoll = new ClassPathResource("db/dataCrapsRoll.json");
        Resource sourceDataGame = new ClassPathResource("db/dataGame.json");
        factory.setResources(new Resource[] { sourceDataPlayer, sourceDataCrapsRoll,sourceDataGame });
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public MongoOperations mongoOperations() {
        MongoOperations mongoOps = new MongoTemplate(mongoDatabaseFactory());
        mongoOps.dropCollection(GameCollection.class);
        mongoOps.dropCollection(CrapsRollCollection.class);
        mongoOps.dropCollection(PlayerCollection.class);
        return mongoOps;
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(), getDatabaseName());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory());
        return mongoTemplate;
    }

    protected String getDatabaseName() {
        return databaseName;
    }
}
