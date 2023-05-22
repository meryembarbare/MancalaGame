package com.mancala.game.dto;

import com.mancala.game.enumeration.PlayerNumberEnum;
import com.mancala.game.enumeration.StatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ResultDto {

    private StatusEnum status;
    private PlayerNumberEnum next;
    private long boardId;
}
