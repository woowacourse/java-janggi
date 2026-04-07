package janggi.domain.status;

import janggi.domain.Boards;
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
    public GameStatus move(Point from, Point to, Boards boards) {
        boards.move(from, to, team);
        if (boards.isKingDie(Team.HAN)) {
            return new FinishedGame(team);
        }
        return new HanTurn();
    }
}
