package com.mancala.game.domain;

import com.mancala.game.enumeration.PlayerNumberEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@Entity
public class Board {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany
    private List<SmallPit> smallPits;

    @OneToMany
    private List<BigPit> bigPits;

    @OneToMany
    private List<Player> players;

    public static Board createBoard() {
        return create(6, 6);
    }

    public static Board create(int numberOfStones, int length) {
        var stones = Stream.generate(() -> numberOfStones)
                .limit(length)
                .collect(Collectors.toList());

        return from(stones, 0, stones, 0);
    }

    public static Board from(List<Integer> p1SmallPits, int p1BigPit, List<Integer> p2SmallPits, int p2BigPit) {
        LinkedList<SmallPit> smallPitsOne = buildSmallPits(PlayerNumberEnum.ONE, p1SmallPits);
        LinkedList<SmallPit> smallPitsTwo = buildSmallPits(PlayerNumberEnum.TWO, p2SmallPits);
        mutuallyOpposite(smallPitsOne, smallPitsTwo);

        BigPit bigPitOne = new BigPit(PlayerNumberEnum.ONE, p1BigPit);
        BigPit bigPitTwo = new BigPit(PlayerNumberEnum.TWO, p2BigPit);

        circular(smallPitsOne, bigPitOne, smallPitsTwo, bigPitTwo);

        Player playerOne = new Player(PlayerNumberEnum.ONE, smallPitsOne, bigPitOne);
        Player playerTwo = new Player(PlayerNumberEnum.TWO, smallPitsTwo, bigPitTwo);

        Board board = new Board();
        board.setSmallPits(new ArrayList<>(smallPitsOne));
        board.setBigPits(List.of(bigPitOne, bigPitTwo));

        board.setPlayers(List.of(playerOne, playerTwo));

        return board;
    }

    private static LinkedList<SmallPit> buildSmallPits(PlayerNumberEnum playerNumber, List<Integer> stones) {
        LinkedList<SmallPit> smallPits = new LinkedList<>();
        smallPits.addLast(new SmallPit(playerNumber, stones.get(0)));
        while (smallPits.size() < stones.size()) {
            SmallPit smallPit = new SmallPit(playerNumber, stones.get(smallPits.size()));
            smallPits.getLast().setNext(smallPit);
            smallPits.addLast(smallPit);
        }
        return smallPits;
    }

    private static void mutuallyOpposite(List<SmallPit> smallPitsOne, List<SmallPit> smallPitsTwo) {
        for (int i = 0; i < smallPitsOne.size(); i++) {
            SmallPit one = smallPitsOne.get(i);
            SmallPit two = smallPitsTwo.get(smallPitsTwo.size() - i - 1);
            one.setOpposite(two);
            two.setOpposite(one);
        }
    }

    private static void circular(LinkedList<SmallPit> smallPitsOne, BigPit storeOne, LinkedList<SmallPit> smallPitsTwo, BigPit storeTwo) {
        smallPitsOne.getLast().setNext(storeOne);
        storeOne.setNext(smallPitsTwo.getFirst());
        smallPitsTwo.getLast().setNext(storeTwo);
        storeTwo.setNext(smallPitsOne.getFirst());
    }

}
