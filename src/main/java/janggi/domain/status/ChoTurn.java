package janggi.domain.status;

import janggi.domain.Board;
import janggi.domain.Point;

public class ChoTurn implements GameStatus{

    private Team team;

    public ChoTurn() {
        this.team = Team.CHO;
    }

    @Override
    public GameStatus move(Point from, Point to, Board board) {
        if (!board.isSameTeam(from, team)) {
            throw new IllegalArgumentException();
        }
        return new HanTurn();
    }
}
