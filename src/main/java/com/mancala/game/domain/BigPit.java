package com.mancala.game.domain;

import com.mancala.game.enumeration.PlayerNumberEnum;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class BigPit extends Pit {

    public BigPit(PlayerNumberEnum owner) {
        super(owner, 0);
    }

    public BigPit(PlayerNumberEnum owner, int stones) {
        super(owner, stones);
    }

    public BigPit() {}

    public void put(int i) {
        stones += i;
    }

    @Override
    boolean canPut(PlayerNumberEnum player) {
        return player.equals(owner);
    }

}
