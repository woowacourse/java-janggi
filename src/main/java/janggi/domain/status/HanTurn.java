package janggi.domain.status;

import janggi.domain.Boards;
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
    public GameStatus move(Point from, Point to, Boards boards) {
        boards.move(from, to, team);
        if (boards.isKingDie(Team.CHO)) {
            return new FinishedGame(team);
        }
        return new ChoTurn();
    }
}
