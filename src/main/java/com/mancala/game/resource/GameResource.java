package com.mancala.game.resource;

import com.mancala.game.dto.GameDto;
import com.mancala.game.dto.ResultDto;
import com.mancala.game.enumeration.PlayerNumberEnum;
import com.mancala.game.exception.BadTurnException;
import com.mancala.game.exception.GameNotFoundException;
import com.mancala.game.exception.PitException;
import com.mancala.game.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/mancala")
public class GameResource {

    private final GameService gameService;

    @PostMapping("/new-game")
    public void createGame(@RequestBody GameDto gameDto) {
        log.info("start Resource create game with params : {}", gameDto);
        gameService.createNewGame(gameDto);
        //TODO
        log.info("End Resource create game with created game id {}", gameDto.getId());
    }

    @PostMapping("/make-move")
    public ResultDto makeMove(@RequestParam PlayerNumberEnum playerNumber, @RequestParam int smallPit, @RequestParam long gameId) throws BadTurnException, PitException, GameNotFoundException {
        return gameService.moveMove(playerNumber, smallPit, gameId);
    }

}
