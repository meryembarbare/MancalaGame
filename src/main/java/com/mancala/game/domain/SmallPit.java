package com.mancala.game.domain;

import com.mancala.game.enumeration.PlayerNumberEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Optional;

@Getter
@Setter
@Entity
public class SmallPit extends Pit {

    @OneToOne
    private SmallPit opposite;

    public SmallPit(PlayerNumberEnum owner, int stones, Board board) {
        super(owner, stones, board);
    }

    public SmallPit() {
    }

    @Override
    public Integer takeStones() {
        int stones = this.stones;
        this.stones = 0;
        return stones;
    }

    @Override
    public Optional<SmallPit> getOpposite() {
        return Optional.ofNullable(opposite);
    }


    @Override
    boolean canPut(PlayerNumberEnum player) {
        return false;
    }
}
