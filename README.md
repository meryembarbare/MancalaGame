# Mancala Game

## Rules

The game provides Mancala game in java. 
The board has 6 small pits, called smallPits, on each side; and one big pit, at each end. 
The object of the game is to capture more stones than one's opponent.

1. At the beginning of the game, four stones are placed in each smallPit. This is the traditional method.
2. Each player controls the six smallPits and their stones on the player's side of the board. The player's score is the number of stones in the bigPit to their right.
3. Players take turns putting their stones. On a turn, the player removes all stones from one of the smallPits under their control. Moving counter-clockwise, the player drops one stone in each smallPit in turn, including the player's own bigPit but not their opponent's.
4. If the last stone we put lands in an empty smallPit owned by the player, and the opposite smallPit contains stones, both the last stone and the opposite stones are captured and placed into the player's bigPit.
5. If the last taken stone lands in the player's bigPit, the player gets an additional move. There is no limit on the number of moves a player can make in their turn.
6. When one player if no longer stones exist in any of their smallPits, the game ends. The other player moves all remaining stones to their store, and the player with the most stones in their bigPit wins.
7. It is possible for the game to end in equality.

## Code 

In this project, we have the following entities : 
- Pit which is an abstract class that represents both smallPit and bigPit, it has an owner(Player), stones , next Pit and board. 
- SmallPit represents the six pits owned by each player. it extends from Pit class, and overrides needed methods.
- BigBit represents the big pit of each player placed to his right. it extends from Pit class as well, and overrides needed methods.
- Player represents the players of the game. it has a list of smallPits, one bigPit and a playerNumber
- Board is a class defined by a list of smallPits, a list of bigPits, and List of players which will contain the two players.
- Game is defined by a board, status which gives us an idea where are we in the game (Winner one , winner two , equality), and the current player of the game.

This project exposes two rest controllers :
- createGame : responsible for creating a new game, it takes as @RequestBody : gameDto which contains the following attributes : namePlayer1 and namePlayer2.
it creates the smallPits , bigPits ,initializes them, creates players and the board , and then saves the game.
- makeMove : responsible for making a move by a specific player , it takes as requestParams : the gameId already saved, the playerNumber and the smallPit number.
all the mancala games rules are taken in consideration.