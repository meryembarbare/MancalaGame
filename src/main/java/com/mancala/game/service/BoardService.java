package com.mancala.game.service;

import com.mancala.game.domain.BigPit;
import com.mancala.game.domain.Board;
import com.mancala.game.domain.Player;
import com.mancala.game.domain.SmallPit;
import com.mancala.game.enumeration.PlayerNumberEnum;
import com.mancala.game.repository.BigPitRepository;
import com.mancala.game.repository.SmallPitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BigPitRepository bigPitRepository;
    private final SmallPitRepository smallPitRepository;

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

    public Board createBoard(String namePlayer1, String namePlayer2) {
        return create(6, 6, namePlayer1, namePlayer2);
    }

    public Board create(int numberOfStones, int length, String namePlayer1, String namePlayer2) {
        var stones = Stream.generate(() -> numberOfStones)
                .limit(length)
                .collect(Collectors.toList());

        return from(stones, 0, stones, 0, namePlayer1, namePlayer2);
    }

    public Board from(List<Integer> p1SmallPits, int p1BigPit, List<Integer> p2SmallPits, int p2BigPit, String namePlayer1, String namePlayer2) {
        LinkedList<SmallPit> smallPitsOne = buildSmallPits(PlayerNumberEnum.ONE, p1SmallPits);
        LinkedList<SmallPit> smallPitsTwo = buildSmallPits(PlayerNumberEnum.TWO, p2SmallPits);

        smallPitRepository.saveAll(smallPitsOne);
        smallPitRepository.saveAll(smallPitsTwo);

        mutuallyOpposite(smallPitsOne, smallPitsTwo);

        BigPit bigPitOne = new BigPit(PlayerNumberEnum.ONE, p1BigPit);
        BigPit bigPitTwo = new BigPit(PlayerNumberEnum.TWO, p2BigPit);
        bigPitRepository.saveAll(List.of(bigPitOne, bigPitTwo));

        circular(smallPitsOne, bigPitOne, smallPitsTwo, bigPitTwo);

        Player playerOne = new Player(PlayerNumberEnum.ONE, smallPitsOne, bigPitOne, namePlayer1);
        Player playerTwo = new Player(PlayerNumberEnum.TWO, smallPitsTwo, bigPitTwo, namePlayer2);

        Board board = new Board();
        board.setSmallPits(new ArrayList<>(smallPitsOne));
        board.setBigPits(List.of(bigPitOne, bigPitTwo));

        board.setPlayers(List.of(playerOne, playerTwo));

        return board;
    }
}
