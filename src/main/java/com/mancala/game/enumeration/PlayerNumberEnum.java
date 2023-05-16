package com.mancala.game.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlayerNumberEnum {
    ONE("First"),
    TWO("Second");

    private final String number;

}
