package janggi.domain;

import java.util.List;

public class Pho implements Piece {

    private final Team team;

    public Pho(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return false;
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        return List.of();
    }

    @Override
    public boolean isSameTeam(Team team) {
        return false;
    }
}
