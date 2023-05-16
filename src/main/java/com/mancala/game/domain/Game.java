package com.mancala.game.domain;

import com.mancala.game.dto.ResultDto;
import com.mancala.game.exception.BadTurnException;
import com.mancala.game.enumeration.PlayerNumberEnum;
import com.mancala.game.enumeration.StatusEnum;
import com.mancala.game.exception.PitException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
@Entity
public class Game {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Board board;

    @OneToOne
    private Player player;

    private StatusEnum status;

    public static Game create(Board board) {
        Game game = new Game();
        game.setBoard(board);
        game.setPlayer(board.getPlayers().get(0));
        game.setStatus(StatusEnum.ACTIVE);
        return game;
    }

    public ResultDto move(PlayerNumberEnum playerNumber, int smallPit) throws BadTurnException, PitException {
        if (!player.getPlayerNumber().equals(playerNumber)) {
            throw new BadTurnException(String.format("Player %s cannot take their turn yet", playerNumber));
        }
        Pit landed = player.turn(smallPit);
        if (player.complete()) {
            otherPlayer().finish();
            status = declareWinner();
        }
        player = nextPlayer(landed);
        return new ResultDto(status, player.getPlayerNumber(), board);
    }

    private StatusEnum declareWinner() {
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

    public Player nextPlayer(Pit landed) {
        if (landed.equals(player.getBigPit())) {
            return player;
        }
        return otherPlayer();
    }

    private Player otherPlayer() {
        List<Player> players = board.getPlayers();
        if (PlayerNumberEnum.ONE.equals(player.getPlayerNumber())) {
            return players.get(0);
        } else {
            return players.get(1);
        }
    }

}
