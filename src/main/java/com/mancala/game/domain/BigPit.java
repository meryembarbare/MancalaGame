package com.mancala.game.domain;

import com.mancala.game.enumeration.PlayerNumberEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class BigPit extends Pit {

    public BigPit(PlayerNumberEnum owner, Board board) {
        super(owner, 0, board);
    }

    public BigPit(PlayerNumberEnum owner, int stones, Board board) {
        super(owner, stones, board);
    }

    public BigPit() {
    }

    public void put(int i) {
        stones += i;
    }

    @Override
    boolean canPut(PlayerNumberEnum player) {
        return player.equals(owner);
    }

}
