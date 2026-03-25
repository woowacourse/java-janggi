package janggi.domain.status;

import janggi.domain.Point;

public class HanTurn implements GameStatus {

    private Team team;

    public HanTurn() {
        this.team = Team.HAN;
    }

    @Override
    public GameStatus move(Point from, Point to) {
        return new ChoTurn();
    }
}
