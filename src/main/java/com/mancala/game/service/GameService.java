package com.mancala.game.service;

import com.mancala.game.domain.Board;
import com.mancala.game.domain.Game;
import com.mancala.game.domain.Pit;
import com.mancala.game.domain.Player;
import com.mancala.game.dto.GameDto;
import com.mancala.game.dto.ResultDto;
import com.mancala.game.enumeration.PlayerNumberEnum;
import com.mancala.game.enumeration.StatusEnum;
import com.mancala.game.exception.BadTurnException;
import com.mancala.game.exception.GameNotFoundException;
import com.mancala.game.exception.PitException;
import com.mancala.game.repository.BoardRepository;
import com.mancala.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    public Game createNewGame(GameDto gameDto) {
        Game game = new Game();
        Board board = boardService.createBoard(gameDto.getNamePlayer1(), gameDto.getNamePlayer2());
        game.setBoard(board);
        game.setPlayer(board.getPlayers().get(0));
        game.setStatus(StatusEnum.ACTIVE);
        game = gameRepository.save(game);
        //GameDto gameDto =  To map
        return game;
    }

    public ResultDto moveMove(PlayerNumberEnum playerNumber, int smallPit, long gameId) throws BadTurnException, GameNotFoundException, PitException {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isEmpty()) {
            throw new GameNotFoundException(String.format("The game with given id %s could not be found", gameId));
        }
        Player player = game.get().getPlayer();
        StatusEnum status = game.get().getStatus();
        Board board = game.get().getBoard();
        if (!game.get().getPlayer().getPlayerNumber().equals(playerNumber)) {
            throw new BadTurnException(String.format("Player %s cannot take their turn yet", playerNumber));
        }
        Pit landed = player.turn(smallPit);
        if (player.complete()) {
            otherPlayer(player, board).finish();
            status = declareWinner(board);
        }
        player = nextPlayer(landed, player, board);
        game.get().setPlayer(player);
        gameRepository.save(game.get());
        return new ResultDto(status, player.getPlayerNumber(), board.getId());
    }

    private StatusEnum declareWinner(Board board) {
        List<Player> players = board.getPlayers();
        int score1 = players.get(0).score();
        int score2 = players.get(1).score();
        if (score1 > score2) {
            return StatusEnum.PLAYER_ONE_WIN;
        }
        if (score2 > score1) {
            return StatusEnum.PLAYER_TWO_WIN;
        }
        return StatusEnum.DRAW;
    }

    public Player nextPlayer(Pit landed, Player player, Board board) {
        if (landed.equals(player.getBigPit())) {
            return player;
        }
        return otherPlayer(player, board);
    }

    private Player otherPlayer(Player player, Board board) {
        List<Player> players = board.getPlayers();
        if (PlayerNumberEnum.ONE.equals(player.getPlayerNumber())) {
            return players.get(1);
        } else {
            return players.get(0);
        }
    }

}
