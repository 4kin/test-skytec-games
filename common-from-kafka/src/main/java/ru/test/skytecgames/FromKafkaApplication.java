package ru.test.skytecgames;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
public class FromKafkaApplication {
    @Value("${kafka.group.name}")
    private String groupName;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    @Value("${kafka.topic.name}")
    private String topicName;
    public static void main(String[] args) {
        SpringApplication.run(FromKafkaApplication.class);
    }

    @Bean
    public KafkaConsumer<String, String> kafkaConsumerFromTopicSkytecGames() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put("offsets.topic.replication.factor", 1);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topicName));

        return consumer;
    }
}