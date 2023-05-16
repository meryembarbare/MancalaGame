package com.mancala.game.domain;

import com.mancala.game.enumeration.PlayerNumberEnum;
import com.mancala.game.exception.PitException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Player {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private PlayerNumberEnum playerNumber;
    @OneToMany
    private List<SmallPit> smallPits;
    @OneToOne
    private BigPit bigPit;

    public Player(PlayerNumberEnum playerNumber, List<SmallPit> smallPits, BigPit bigPit) {
        this.playerNumber = playerNumber;
        this.smallPits = smallPits;
        this.bigPit = bigPit;
    }

    public Player() {}

    public Pit turn(int smallPitNum) throws PitException {
        SmallPit smallPit = getSmallPit(smallPitNum);
        checkHasStones(smallPit);
        Pit pit = takeTurn(smallPit);
        if (shouldCaptureOpposite(pit)) {
            bigPit.put(pit.takeStones());
            bigPit.put(pit.capture());
        }
        return pit;
    }

    public boolean complete() {
        return smallPits.stream().allMatch(SmallPit::isEmpty);
    }

    public void finish() {
        for (SmallPit smallPit : smallPits) {
            bigPit.put(smallPit.takeStones());
        }
    }

    public int score() {
        return bigPit.countStones();
    }

    private boolean shouldCaptureOpposite(Pit pit) {
        return pit.countStones() == 1 && pit.getOpposite().isPresent();
    }

    private void checkHasStones(SmallPit smallPit) throws PitException {
        if (smallPit.isEmpty()) {
            throw new PitException("SmallPit must have stones to take turn");
        }
    }

    private Pit takeTurn(SmallPit smallPit) {
        Integer stones = smallPit.takeStones();
        Pit pit = smallPit;
        while (stones > 0) {
            pit = pit.getNext();
            if (pit.canPut(playerNumber)) {
                stones--;
                pit.put();
            }
        }
        return pit;
    }

    private SmallPit getSmallPit(int smallPitNum) throws PitException {
        if (smallPitNum < 1 || smallPitNum > smallPits.size()) {
            throw new PitException("SmallPit number must be between 1 and " + smallPits.size());
        }
        return this.smallPits.get(smallPitNum - 1);
    }
}
