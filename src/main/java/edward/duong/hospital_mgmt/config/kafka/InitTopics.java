package edward.duong.hospital_mgmt.config.kafka;

import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class InitTopics {
    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Value("${app.kafka.topics.user-activity.name}")
    private String topicName;

    @Value("${app.kafka.topics.user-activity.partitions}")
    private int partitions;

    @Value("${app.kafka.topics.user-activity.replication}")
    private short replicationFactor;

    @Value("${app.kafka.topics.user-activity.retention-ms}")
    private String retentionMs;

    @PostConstruct
    public void initTopics() {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            ListTopicsResult listTopics = adminClient.listTopics();
            boolean topicExists = listTopics.names().get().contains(topicName);

            if (!topicExists) {
                adminClient
                        .createTopics(Collections.singleton(TopicBuilder.name(topicName)
                                .partitions(partitions)
                                .replicas(replicationFactor)
                                .config("retention.ms", retentionMs)
                                .build()))
                        .all()
                        .get();
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Failed to create topic: " + e.getMessage());
        }
    }
}
