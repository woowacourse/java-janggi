package janggi.domain.piece;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Jang implements Piece {

    private final Team team;
    private final PieceType type;

    public Jang(Team team) {
        this.team = team;
        this.type = PieceType.JANG;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getX() - from.getX();
        int pathY = to.getY() - from.getY();

        if (pathX == 1 && pathY == 1) {
            return List.of(to);
        }
        if (pathX == 1 && pathY == 0) {
            return List.of(to);
        }
        if (pathY == 1 && pathX == 0) {
            return List.of(to);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.stream()
                .filter(piece -> piece.isSameTeam(team))
                .count() == 0;
    }

    @Override
    public boolean isSameType(PieceType type) {
        return this.type.equals(type);
    }
}
