package janggi.domain.status;

import janggi.domain.Board;
import janggi.domain.Point;

public class ChoTurn implements GameStatus {

    private final Team team;

    public ChoTurn() {
        this.team = Team.CHO;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public GameStatus move(Point from, Point to, Board board) {
        board.move(from, to, team);
        if (board.isKingDie(Team.HAN)) {
            return new FinishedGame(team);
        }
        return new HanTurn();
    }
}
