package org.tictactoe.strategies;

import org.tictactoe.models.Board;
import org.tictactoe.models.Cell;
import org.tictactoe.models.CellState;
import org.tictactoe.models.Move;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Move makeMove(Board board) {
        //List<List<Cell>> cell = board.getCells();
        for(List<Cell> cellRow : board.getCells()){
            for(Cell c : cellRow){
                if(c.getCellState().equals(CellState.EMPTY)){
                    return new Move(c, null);
                }
            }
        }
        return null;
    }
}
