package ru.test.skytecgames.listeners;

public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(topics = "${kafka.topic.name}")
    public void listenerForTopic(String message) {
        System.out.println(message);
    }
}