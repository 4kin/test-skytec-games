package ru.test.skytecgames.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Clan {
    private long id;
    private String name;
    private int gold;

}