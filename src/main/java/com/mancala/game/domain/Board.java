package com.mancala.game.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_id_seq")
    @SequenceGenerator(name = "board_id_seq", sequenceName = "board_id_seq", allocationSize = 1)
    private Long id;

    @OneToMany
    @JoinColumn(name = "board_id")
    private List<SmallPit> smallPits;

    @OneToMany
    @JoinColumn(name = "board_id")
    private List<BigPit> bigPits;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private List<Player> players;


}
