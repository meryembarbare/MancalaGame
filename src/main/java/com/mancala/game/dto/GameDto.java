package com.mancala.game.dto;

import com.mancala.game.enumeration.PlayerNumberEnum;
import com.mancala.game.enumeration.StatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GameDto {

    private Long id;
    private int smallPitId;
    private String playerNumber;
    private PlayerNumberEnum player;
    private StatusEnum status;
    private String namePlayer1;
    private String namePlayer2;
}
