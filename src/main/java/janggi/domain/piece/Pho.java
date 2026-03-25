package janggi.domain.piece;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Pho implements Piece {

    private final Team team;
    private final String name;

    public Pho(Team team, String pieceName) {
        this.team = team;
        this.name = pieceName;
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
