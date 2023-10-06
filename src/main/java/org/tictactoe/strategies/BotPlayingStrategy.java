package org.tictactoe.strategies;

import org.tictactoe.models.Board;
import org.tictactoe.models.Move;

public interface BotPlayingStrategy {
    public Move makeMove(Board board);
}
