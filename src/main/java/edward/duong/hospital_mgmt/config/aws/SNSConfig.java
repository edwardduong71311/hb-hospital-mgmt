package edward.duong.hospital_mgmt.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

@Configuration
public class SNSConfig {
    @Value("${cloud.aws.region}")
    private String region;

    @Value("${cloud.aws.local-url:}")
    private String url;

    @Bean
    @Profile("local")
    public SnsClient snsClientLocal() {
        return SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .endpointOverride(URI.create(url))
                .build();
    }

    @Bean
    @Profile("prod")
    public SnsClient snsClientProd() {
        return SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
