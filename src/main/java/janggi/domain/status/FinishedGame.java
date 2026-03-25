package janggi.domain.status;

import janggi.domain.Board;
import janggi.domain.Point;

public class FinishedGame implements GameStatus {

    private final Team winner;

    public FinishedGame(Team winner) {
        this.winner = winner;
    }

    @Override
    public GameStatus move(Point from, Point to, Board board) {
        throw new RuntimeException("게임이 종료되었습니다.\n 승자는 "+ winner.name());
    }
}
