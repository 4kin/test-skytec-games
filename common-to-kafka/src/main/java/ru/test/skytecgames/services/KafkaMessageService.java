package ru.test.skytecgames.services;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@Service
public class KafkaMessageService {

    private final KafkaTemplate kafkaTemplate;

    @Value("${kafka.topic.name}")
    String kafkaTopic;

    public KafkaMessageService(@Qualifier("kafkaTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String clanId, String gold) {
        ProducerRecord<String, String> record = new ProducerRecord<>(kafkaTopic, clanId, gold);
//        log.info("Message " + record.value() + " key " + record.key());

        kafkaTemplate.send(record);
    }

    public String sendMessageAndWaitResult(String message) {
        AtomicReference<String> returnString = new AtomicReference<>("");
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaTopic, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                returnString.set(message + " " + result.getRecordMetadata().offset());
            } else {
                returnString.set(ex.getMessage());
            }
        });
        return returnString.get();
    }
}