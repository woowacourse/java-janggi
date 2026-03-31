package janggi.domain.status;

import janggi.domain.board.Board;
import janggi.domain.point.Point;

public class FinishedGame implements GameStatus {

    private final Team winner;

    public FinishedGame(Team winner) {
        this.winner = winner;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Team getTeam() {
        return winner;
    }

    @Override
    public GameStatus move(Point from, Point to, Board board) {
        throw new IllegalStateException("[ERROR] 이미 게임이 종료되었습니다.\n 승자는 "+ winner.name());
    }
}
