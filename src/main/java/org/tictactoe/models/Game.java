package org.tictactoe.models;

import org.tictactoe.exceptions.BotCountException;
import org.tictactoe.exceptions.DuplicateSymbolException;
import org.tictactoe.exceptions.PlayerCountException;
import org.tictactoe.strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private List<WinningStrategy> winningStrategies;
    private Player winner;
    private GameState gameState;
    private int nextPlayerIndex;
    private int dimensions;

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    private Game(List<Player> players, int dimensions, List<WinningStrategy> winningStrategies){
        this.players = players;
        this.dimensions = dimensions;
        this.winningStrategies = winningStrategies;
        this.board = new Board(dimensions);
        this.moves = new ArrayList<>();
        //this.winner = new Player();
        this.gameState = GameState.IN_PROGRESS;
        this.nextPlayerIndex = 0;
    }
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }
    public static Builder getBuilder(){
        return new Builder();
    }
    public static class Builder{
        private List<Player> players;
        private int dimensions;
        private List<WinningStrategy> winningStrategies;

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setDimensions(int dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        private void validateBotCount() throws BotCountException{
            int botCnt=0;
            for(Player p: players){
                if(p.getPlayerType().equals("BOT")){
                    botCnt++;
                }
            }
            if(botCnt > 1){
                throw new BotCountException();
            }
        }
        private void validateSymbol() throws DuplicateSymbolException{
            HashSet<Character> hs = new HashSet<>();
            for(Player p: players){
                if(hs.contains(p.getSymbol().getSymbol())){
                    throw new DuplicateSymbolException();
                }else{
                    hs.add(p.getSymbol().getSymbol());
                }
            }
        }
        private void validatePlayerCount() throws PlayerCountException{
            if(players.size() != dimensions-1){
                throw new PlayerCountException();
            }
        }
        public void validate() throws BotCountException, DuplicateSymbolException, PlayerCountException {
            validateBotCount();
            validateSymbol();
            validatePlayerCount();
        }

        public Game build() throws PlayerCountException, DuplicateSymbolException, BotCountException {
            validate();
            return new Game(players, dimensions,winningStrategies);
        }

    }
    private boolean validateMove(Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        if(row >= board.getSize() || col >= board.getSize()) {
            return false;
        }
        if(!board.getCells().get(row).get(col).getCellState().equals(CellState.EMPTY)) {
            return false;
        }
        return true;
    }
    private boolean checkWinner(Board board, Move move) {
        for(WinningStrategy winningStrategy: winningStrategies) {
            if(winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }
        return false;
    }
    public void makeMove() {
        Player currentMovePlayer = players.get(nextPlayerIndex);//just getting which player is next to move

        System.out.println("It is " + currentMovePlayer.getName() + "'s turn. Please make your move");

        Move currentPlayerMove = currentMovePlayer.makeMove(board);//populating move object by taking row,col from Player
        if(!validateMove(currentPlayerMove)) {
            System.out.println("Invalid move. Please try again");
            return;
        }

        int row = currentPlayerMove.getCell().getRow();
        int col = currentPlayerMove.getCell().getCol();

        Cell cellToChange = board.getCells().get(row).get(col);
        cellToChange.setCellState(CellState.FILLED);
        cellToChange.setPlayer(currentMovePlayer);

        Move finalMoveObject = new Move(cellToChange, currentMovePlayer);// we are taking cell from the board
        moves.add(finalMoveObject);// storing for undo operation

        nextPlayerIndex += 1;
        nextPlayerIndex %= players.size();//to make the nextplayerindex in clockwise once it exceeds the players size

        if(checkWinner(board, finalMoveObject)) {
            gameState = GameState.WIN;
            winner = currentMovePlayer;
        } else if(moves.size() == this.board.getSize() * this.board.getSize()) {
            gameState = GameState.DRAW;
        }
    }
    public void printBoard(){
        board.printBoard();
    }
    public void undo(){
        if(moves.isEmpty()){
            System.out.println("No move to Undo");
            return;
        }
        Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);
        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        for(WinningStrategy winningStrategy: winningStrategies){
            winningStrategy.undo(board, lastMove);
        }
        nextPlayerIndex -=1;
        nextPlayerIndex = (nextPlayerIndex + players.size())%players.size(); // to avoid -ve values
    }
}
