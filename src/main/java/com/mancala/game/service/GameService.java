package com.mancala.game.service;

import com.mancala.game.domain.Board;
import com.mancala.game.domain.Game;
import com.mancala.game.enumeration.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

    public static Game createNewGame() {
        Game game = new Game();
        Board board = Board.createBoard();
        game.setBoard(board);
        game.setPlayer(board.getPlayers().get(0));
        game.setStatus(StatusEnum.ACTIVE);
        return game;
    }
}
