package org.tictactoe.Controller;

import org.tictactoe.exceptions.BotCountException;
import org.tictactoe.exceptions.DuplicateSymbolException;
import org.tictactoe.exceptions.PlayerCountException;
import org.tictactoe.models.Game;
import org.tictactoe.models.GameState;
import org.tictactoe.models.Player;
import org.tictactoe.strategies.WinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int dimensions, List<Player> players, List<WinningStrategy> winningStrategyList) throws PlayerCountException, DuplicateSymbolException, BotCountException {
        return Game.getBuilder().
                setDimensions(dimensions).
                setPlayers(players).
                setWinningStrategies(winningStrategyList).
                build();
    }
    public GameState checkGameState(Game game){
        return game.getGameState();
    }
    public void printBoard(Game game){
        game.printBoard();
    }
    public void makeMove(Game game){
        game.makeMove();
    }
    public void makeUndo(Game game){
        game.undo();
    }
    public String getWinner(Game game){
        return game.getWinner().getName();
    }
}
