package ru.test.skytecgames.services;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class KafkaMessageService {

    private final KafkaTemplate kafkaTemplate;

    @Value("${kafka.topic.name}")
    String kafkaTopic;

    public KafkaMessageService(@Qualifier("kafkaTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String clanId, String gold) {
//        NewGold newGold = new NewGold(clanId,gold);
        ProducerRecord<String, String> record = new ProducerRecord<>(kafkaTopic, clanId, gold);
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