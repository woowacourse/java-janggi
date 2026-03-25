package janggi.domain.status;

import janggi.domain.Board;
import janggi.domain.Point;

public class HanTurn implements GameStatus {

    private Team team;

    public HanTurn() {
        this.team = Team.HAN;
    }

    @Override
    public GameStatus move(Point from, Point to, Board board) {
        if (!board.isSameTeam(from, team)) {
            throw new IllegalArgumentException();
        }
        return new ChoTurn();
    }
}
