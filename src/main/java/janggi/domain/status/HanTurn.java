package janggi.domain.status;

import janggi.domain.Board;
import janggi.domain.Point;

public class HanTurn implements GameStatus {

    private final Team team;

    public HanTurn() {
        this.team = Team.HAN;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public GameStatus move(Point from, Point to, Board board) {
        board.move(from, to, team);
        if (board.isKingDie()) {
            return new FinishedGame(team);
        }
        return new ChoTurn();
    }
}
