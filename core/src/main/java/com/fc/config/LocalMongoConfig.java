package com.fc.config;

import com.mongodb.ConnectionString;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
@Slf4j
public class LocalMongoConfig {

    private static final String MONGODB_IMAGE_NAME = "mongo:5.0";
    private static final int MONGODB_INNER_PORT = 27017;
    private static final String DATABASE_NAME = "notification";
    private static final GenericContainer mongoContainer = createMongoInstance();


    private static GenericContainer createMongoInstance() {
        return new GenericContainer(DockerImageName.parse(MONGODB_IMAGE_NAME))
            .withExposedPorts(MONGODB_INNER_PORT)
            .withReuse(true);
    }


    @PostConstruct
    public void startMongo() {
        try {
            mongoContainer.start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @PreDestroy
    public void stopMongo() {
        try {
            mongoContainer.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Bean
    public MongoDatabaseFactory notificationMongoFactory() {
        return new SimpleMongoClientDatabaseFactory(
            connectionString()
        );
    }

    private ConnectionString connectionString() {
        String host = mongoContainer.getHost();
        Integer port = mongoContainer.getMappedPort(
            MONGODB_INNER_PORT); // 여기서 선언한 포트랑은 다름! 내부에서 접근할 때 포트가 다르기 때문에! 테스트 컨테이너는 임의로 지정이 되게 되어있음!
        return new ConnectionString("mongodb://" + host + ":" + port + "/" + DATABASE_NAME);


    }


}
