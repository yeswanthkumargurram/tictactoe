package org.tictactoe;

import org.tictactoe.Controller.GameController;
import org.tictactoe.exceptions.BotCountException;
import org.tictactoe.exceptions.DuplicateSymbolException;
import org.tictactoe.exceptions.PlayerCountException;
import org.tictactoe.models.*;
import org.tictactoe.strategies.ColumnWinningStrategy;
import org.tictactoe.strategies.DiagonalWinningStrategy;
import org.tictactoe.strategies.RowWinningStrategy;
import org.tictactoe.strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);
        int dimensions =3;
        Player p1 = new Player(1,"sai",new Symbol('X'), PlayerType.HUMAN);
        Player p2 = new Bot(2, "Gpt", new Symbol('O'), PlayerType.BOT, BotDifficultyLevel.EASY);
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColumnWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());
        Game game = null;
        try {
            game = gameController.startGame(3,players, winningStrategies);
            while(gameController.checkGameState(game).equals(GameState.IN_PROGRESS)){
                gameController.printBoard(game);
                System.out.println("Does anyone want to do undo? (Y/N)");
                String undoAns = scanner.next();
                if(undoAns.equalsIgnoreCase("y")){
                    gameController.makeUndo(game);
                    continue;//or else code will go for next line of code which is makeMove flow
                }
                gameController.makeMove(game);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        gameController.printBoard(game);
        System.out.println("Game is Finished");
        GameState gameState = gameController.checkGameState(game);
        if(gameState.equals(GameState.WIN)){
            System.out.println("Hurray! Winner is :"+gameController.getWinner(game));
        }else if(gameState.equals(GameState.DRAW)){
            System.out.println("Game is Draw");
        }

    }
}