package me.namila.rx.reactivespringboot.core.configuration;

import me.namila.rx.reactivespringboot.core.repository.GenericRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/** Config for Mongo reactive. */
@Configuration
@EnableReactiveMongoRepositories(
    basePackages = "me.namila.rx.reactivespringboot.core.repository",
    repositoryBaseClass = GenericRepositoryImpl.class)
@EnableMongoAuditing
public class MongoReactive {
  /** The Mongo client. */
  //    MongoClient mongoClient;
  //
  //    @Value("${spring.data.mongodb.database}")
  //    private String databaseName;
  //
  //    /**
  //     * Reactive mongo template reactive mongo template.
  //     *
  //     * @return the reactive mongo template
  //     */
  //    @Bean
  //    public ReactiveMongoTemplate reactiveMongoTemplate() {
  //        return new ReactiveMongoTemplate(mongoClient,databaseName);
  //    }
  //
  //    /**
  //     * Sets mongo client.
  //     *
  //     * @param mongoClient the mongo client
  //     */
  //    @Autowired
  //    public void setMongoClient(MongoClient mongoClient) {
  //        this.mongoClient = mongoClient;
  //    }
}
