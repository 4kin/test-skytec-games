package ru.test.skytecgames.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Clan {

    private long id;
    private String name;
    private int gold;
}