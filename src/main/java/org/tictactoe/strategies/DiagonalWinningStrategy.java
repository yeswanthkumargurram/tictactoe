package org.tictactoe.strategies;

import org.tictactoe.models.Board;
import org.tictactoe.models.Move;
import org.tictactoe.models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{
    Map<Symbol, Integer> leftDiagCntMap = new HashMap<>();
    Map<Symbol, Integer> rightDiagCntmap = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        if(row == col){
            if(!leftDiagCntMap.containsKey(symbol)){
                leftDiagCntMap.put(symbol, 0);
            }
            leftDiagCntMap.put(symbol, leftDiagCntMap.get(symbol)+1);
            if(leftDiagCntMap.get(symbol) == board.getSize()){
                return true;
            }
        }
        if(row+col == board.getSize()-1){
            if(!rightDiagCntmap.containsKey(symbol)){
                rightDiagCntmap.put(symbol, 0);
            }
            rightDiagCntmap.put(symbol, rightDiagCntmap.get(symbol)+1);
            if(rightDiagCntmap.get(symbol) == board.getSize()){
                return true;
            }
        }

        return false;
    }

    @Override
    public void undo(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        if(row == col){
            leftDiagCntMap.put(symbol,
                    leftDiagCntMap.get(symbol)-1);
        }
        if(row+col == board.getSize()-1){
            rightDiagCntmap.put(symbol,
                    rightDiagCntmap.get(symbol)-1);
        }
    }
}
