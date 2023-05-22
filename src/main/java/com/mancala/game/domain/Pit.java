package com.mancala.game.domain;

import com.mancala.game.enumeration.PlayerNumberEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pit_id_seq")
    @SequenceGenerator(name = "pit_id_seq", sequenceName = "pit_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    protected PlayerNumberEnum owner;
    protected int stones;

    @OneToOne
    private Pit next;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    protected Pit(PlayerNumberEnum owner, int stones, Board board) {
        this.stones = stones;
        this.owner = owner;
        this.board = board;
    }

    protected Pit() {}

    public Integer countStones() {
        return stones;
    }

    public Pit nextPit() {
        return next;
    }

    public Pit setNextPit(Pit next) {
        this.next = next;
        return next;
    }

    public void put() {
        this.stones++;
    }

    public PlayerNumberEnum getOwner() {
        return owner;
    }

    abstract boolean canPut(PlayerNumberEnum player);

    public boolean isEmpty() {
        return this.stones == 0;
    }

    public Optional<SmallPit> getOpposite() {
        return Optional.empty();
    }

    public Integer takeStones() {
        return 0;
    }

    public Integer capture() {
        if (this.getOpposite().isEmpty()) {
            return 0;
        }
        return Optional.ofNullable(this.getOpposite())
                .map(item -> item.get().takeStones())
                .orElse(0);
    }

}
