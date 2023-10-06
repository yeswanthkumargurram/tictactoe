package org.tictactoe.strategies;

import org.tictactoe.models.BotDifficultyLevel;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy gtBotPlayingStrategyForDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
        return new EasyBotPlayingStrategy();
    }
}
