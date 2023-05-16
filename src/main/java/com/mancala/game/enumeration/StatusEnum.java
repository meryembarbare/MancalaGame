package com.mancala.game.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {

    ACTIVE("Active"),

    DRAW("Draw"),

    PLAYER_ONE_WIN("Player 1 won the game"),

    PLAYER_TWO_WIN("Player two won the game");

    private final String status;

}
