package com.mancala.game;

import com.mancala.game.domain.*;
import com.mancala.game.dto.GameDto;
import com.mancala.game.enumeration.PlayerNumberEnum;
import com.mancala.game.repository.BoardRepository;
import com.mancala.game.repository.GameRepository;
import com.mancala.game.service.BoardService;
import com.mancala.game.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GameServiceTests {

	@Mock
	private BoardService boardService;

	@Mock
	private BoardRepository boardRepository;

	@Mock
	private GameRepository gameRepository;

	@InjectMocks
	private GameService gameService;

	@Test
	void testCreateNewGame() {
		GameDto gameDto = new GameDto();
		gameDto.setNamePlayer1("Player 1");
		gameDto.setNamePlayer2("Player 2");

		Board mockedBoard = new Board();
		Player player1 = buildPlayer(1L, "Player 1");
		Player player2 = buildPlayer(2L, "Player 2");
		mockedBoard.setId(1L);
		mockedBoard.setPlayers(List.of(player1, player2));
		Mockito.when(boardService.createBoard(gameDto.getNamePlayer1(), gameDto.getNamePlayer2())).thenReturn(mockedBoard);

		Game mockedGame = new Game();
		mockedGame.setId(1L);
		Mockito.when(gameRepository.save(Mockito.any(Game.class))).thenReturn(mockedGame);

		// Calling the method under test
		Long gameId = gameService.createNewGame(gameDto);

		// Verifying the results
		Assertions.assertEquals(mockedGame.getId(), gameId);
		Mockito.verify(boardRepository, Mockito.times(1)).save(mockedBoard);
	}


	private Player buildPlayer(Long id, String name) {
		return new Player(PlayerNumberEnum.ONE, List.of(buildSmallPit()), buildBigPit(),"Player 1");
	}

	private SmallPit buildSmallPit() {
		return new SmallPit(PlayerNumberEnum.ONE, 6);
	}

	private BigPit buildBigPit() {
		return new BigPit(PlayerNumberEnum.ONE);
	}

}
