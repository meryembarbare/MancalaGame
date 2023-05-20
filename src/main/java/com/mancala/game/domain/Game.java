package com.mancala.game.domain;

import com.mancala.game.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_id_seq")
    @SequenceGenerator(name = "game_id_seq", sequenceName = "game_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    private Board board;

    @OneToOne
    private Player player;

    private StatusEnum status;

}
