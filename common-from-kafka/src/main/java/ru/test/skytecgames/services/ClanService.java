package ru.test.skytecgames.services;

import org.springframework.stereotype.Service;
import ru.test.skytecgames.model.Clan;
import ru.test.skytecgames.repo.ClanRepository;

@Service
public class ClanService {

    private final ClanRepository clanRepository;

    public ClanService(ClanRepository clanRepository) {
        this.clanRepository = clanRepository;
    }

    public void addGold(Long idClan, int goldChange) {

        Clan clan = new Clan().builder().id(idClan).gold(goldChange).build();

        // Сохраняем в БД данные об измени денег у клана. А в БД отработает тригер (increase_gold_with_old_value_trigger) и сложит передаваемое изменния денег
        clanRepository.save(clan);
    }
}