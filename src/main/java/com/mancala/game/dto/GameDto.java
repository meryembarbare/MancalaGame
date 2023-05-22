package com.mancala.game.dto;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GameDto {

    private Long id;
    private String namePlayer1;
    private String namePlayer2;
}
