package ru.test.skytecgames.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.skytecgames.model.Clan;

public interface ClanRepository extends JpaRepository<Clan, Long> {
}