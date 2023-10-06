package org.tictactoe.strategies;

import org.tictactoe.models.Board;
import org.tictactoe.models.Move;
import org.tictactoe.models.Symbol;

import java.util.*;

public class RowWinningStrategy implements WinningStrategy{
    private Map<Integer, Map<Symbol, Integer>> countMap = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row= move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();
        if(!countMap.containsKey(row)){
            countMap.put(row, new HashMap<>());
        }
        Map<Symbol, Integer> rowMap = countMap.get(row);
        if(!rowMap.containsKey(symbol)){
            rowMap.put(symbol, 0);
        }
        rowMap.put(symbol, rowMap.get(symbol)+1);
        if(rowMap.get(symbol) == board.getSize()){
            return true;
        }
        return false;
    }

    @Override
    public void undo(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();
        Map<Symbol, Integer> m = countMap.get(row);
        m.put(symbol, m.get(symbol)-1);
    }
}
