package edward.duong.hospital_mgmt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@ContextConfiguration(classes = { DisableSecurityConfig.class })
public abstract class IntegrationTestConfig extends BaseWebTestConfig {
    static MongoDBContainer mongo;
    static PostgreSQLContainer<?> postgres;
    static {
        String profile = System.getProperty("spring.profiles.active");
        if ("mongo".equals(profile)) {
            mongo = new MongoDBContainer("mongo:8.0");
        }
        if ("postgres".equals(profile)) {
            postgres = new PostgreSQLContainer<>(DockerImageName.parse(
                    "postgis/postgis:17-3.5")
                    .asCompatibleSubstituteFor("postgres"));
        }
    }

    @DynamicPropertySource
    static void containersProperties(DynamicPropertyRegistry registry) {
        String profile = System.getProperty("spring.profiles.active");

        if ("mongo".equals(profile)) {
            mongo.start();
            registry.add("app.feature.database", () -> "mongo");
            registry.add("spring.data.mongodb.uri", () -> mongo.getReplicaSetUrl());
        }
        if ("postgres".equals(profile)) {
            postgres.start();

            registry.add("app.feature.database", () -> "postgres");
            registry.add("spring.datasource.url", postgres::getJdbcUrl);
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
        }
    }
}
