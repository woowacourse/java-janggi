package model;

import model.board.Board;
import model.board.Country;
import model.move.Move;

public class JanggiGame {
    private final int id;
    private final Board board;
    private Country turn;

    public JanggiGame(int id, Board board, Country turn) {
        this.id = id;
        this.board = board;
        this.turn = turn;
    }

    public void nextTurn() {
        this.turn = turn.convertCountry();
    }

    public boolean isProgressing() {
        return board.endCondition();
    }

    public void move(Move move) {
        board.move(move);
    }

    public int id() {
        return id;
    }

    public Board board() {
        return board;
    }

    public Country turn() {
        return turn;
    }
}