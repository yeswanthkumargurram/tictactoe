package org.tictactoe.strategies;

import org.tictactoe.models.Board;
import org.tictactoe.models.Move;

public interface WinningStrategy {
    public boolean checkWinner(Board board, Move move);
    public void undo(Board board, Move move);
}
