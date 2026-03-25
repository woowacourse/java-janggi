package janggi.domain;

import janggi.domain.status.ChoTurn;
import janggi.domain.status.GameStatus;

public class JanggiGame {

    private final Board board;
    private GameStatus gameStatus;

    public JanggiGame(Board board) {
        this.board = board;
        this.gameStatus = new ChoTurn();
    }

    public void play(Point from, Point to) {
        this.gameStatus = gameStatus.move(from, to, board);
    }
}
