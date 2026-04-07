package janggi.domain.status;

import janggi.domain.Boards;
import janggi.domain.Point;

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
    public GameStatus move(Point from, Point to, Boards boards) {
        throw new IllegalStateException("[ERROR] 게임이 종료되었습니다. 승자는 " + winner.name() + "입니다.");
    }
}
