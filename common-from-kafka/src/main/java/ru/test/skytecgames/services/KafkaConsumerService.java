package ru.test.skytecgames.services;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Component
public class KafkaConsumerService {

    private final KafkaConsumer kafkaConsumerFromTopicSkytecGames;
    private final ClanService clanService;

    public KafkaConsumerService(KafkaConsumer kafkaConsumerFromTopicSkytecGames,
                                ClanService clanService) {
        this.kafkaConsumerFromTopicSkytecGames = kafkaConsumerFromTopicSkytecGames;
        this.clanService = clanService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void readDataFromKafka() {


        Duration timeout = Duration.ofMillis(100);
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumerFromTopicSkytecGames.poll(timeout);
//            log.info("Записей всего выбрано = " + records.count());

            for (TopicPartition partition : records.partitions()) {
                Map<String, Integer> collected = records.records(partition).stream().collect(Collectors.groupingBy(record ->
                        record.key(), Collectors.summingInt(record -> Integer.parseInt(record.value()))));
                for (Map.Entry<String, Integer> entry : collected.entrySet()) {
//                    log.info(String.format("PARTITION = %s ID = %s SUM = %s", partition, entry.getKey(), entry.getValue()));

                    clanService.addGold(Long.parseLong(entry.getKey()), entry.getValue());
                }
            }
            kafkaConsumerFromTopicSkytecGames.commitSync();
        }
    }
}