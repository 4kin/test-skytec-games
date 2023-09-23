package ru.test.skytecgames.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.test.skytecgames.services.KafkaMessageService;

@RestController
public class KafkaController {
    private final KafkaMessageService kafkaMessageService;

    public KafkaController(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    @GetMapping("/clan_id/{clanId}/gold/{gold}")
    public void sendMessage(@PathVariable String clanId, @PathVariable String gold) {
        kafkaMessageService.sendMessage(clanId, gold);
    }

}