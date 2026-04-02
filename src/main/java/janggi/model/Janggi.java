package janggi.model;

import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import janggi.model.turn.ChoTurn;
import janggi.model.turn.Turn;
import java.util.Map;

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

    public Map<Position, Piece> getBoard() {
        return turn.getBoard();
    }

    public boolean isChoTurn() {
        return turn.isChoTurn();
    }
}
