package janggi.model;

import janggi.model.board.Board;
import janggi.model.position.absolute.Position;
import janggi.model.turn.GameOver;
import janggi.model.turn.Turn;
import janggi.model.turn.playing.ChoTurn;

public class Janggi {

    private final Turn turn;

    private Janggi(Turn turn) {
        this.turn = turn;
    }

    public static Janggi of(Board board) {
        return new Janggi(new ChoTurn(board));
    }

    public Janggi play(Position from, Position to) {
        return new Janggi(turn.play(from, to));
    }

    public boolean isGameOver() {
        return turn.isGameOver();
    }

    public Board getBoard() {
        return turn.board();
    }

    public boolean isChoTurn() {
        return turn.isChoTurn();
    }

    public Team getWinner() {
        return turn.getWinner();
    }

    public Janggi draw() {
        return new Janggi(new GameOver(getBoard()));
    }
}
