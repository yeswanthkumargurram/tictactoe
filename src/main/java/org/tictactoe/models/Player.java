package org.tictactoe.models;

import java.util.Scanner;

public class Player {
    private String name;
    private int id;
    private Symbol symbol;
    private PlayerType playerType;
    private Scanner sc;
    public Player(int id, String name, Symbol symbol, PlayerType playerType){
        this.id= id;
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
        this.sc=new Scanner(System.in);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
    public Move makeMove(Board board) {
        System.out.println("Please enter the row where you want to make the move");
        int row = sc.nextInt();

        System.out.println("Please enter the col where you want to make the move");
        int col = sc.nextInt();

        return new Move(new Cell(row, col), this);
    }
}
