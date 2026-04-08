package janggi.model;

import janggi.model.board.Board;
import janggi.model.position.Position;
import janggi.model.turn.ChoTurn;
import janggi.model.turn.HanTurn;
import janggi.model.turn.Turn;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Janggi {

    private final Turn turn;

    private Janggi(Turn turn) {
        this.turn = turn;
    }

    public static Janggi of(Board board) {
        return new Janggi(new ChoTurn(board));
    }

    public static Janggi of(Board board, Team currentTurn) {
        if (currentTurn == Team.CHO) {
            return new Janggi(new ChoTurn(board));
        }
        return new Janggi(new HanTurn(board));
    }

    public Janggi play(Position from, Position to) {
        return new Janggi(turn.play(from, to));
    }

    public boolean isGameOver() {
        return turn.isGameOver();
    }

    public void withBoard(BiConsumer<Board, Team> consumer) {
        turn.withBoard(consumer);
    }

    public void withScore(BiConsumer<Score, Score> consumer) {
        turn.withScore(consumer);
    }

    public void withWinner(Consumer<Team> consumer) {
        turn.withWinner(consumer);
    }
}
