package ru.test.skytecgames.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Clan {
    @Id
    private long id;
    private String name;
    private int gold;
}