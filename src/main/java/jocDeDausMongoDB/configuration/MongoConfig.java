package jocDeDausMongoDB.configuration;

import com.mongodb.client.MongoClients;
import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.collection.GameCollection;
import jocDeDausMongoDB.collection.PlayerCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
@ComponentScan(basePackages = {"jocDeDausMongoDB"})
@EnableMongoRepositories(basePackages={"jocDeDausMongoDB.repository"})
public class MongoConfig {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

 /*
    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;



    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Autowired
    private Environment env;
   */

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
