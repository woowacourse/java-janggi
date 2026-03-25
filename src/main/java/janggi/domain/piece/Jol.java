package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Jol implements Piece {

    private final Team team;
    private final PieceType type;

    public Jol(Team team) {
        this.team = team;
        this.type = PieceType.JOL;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getX() - from.getX();
        int pathY = to.getY() - from.getY();

        if (team.equals(Team.CHO)) {
            if ((pathY == 1 && pathX == 0) ||
                    (abs(pathX) == 1 && pathY == 0)
            ) {
                return List.of(to);
            }
            throw new IllegalArgumentException();
        }

        if ((pathY == -1 && pathX == 0) ||
                (abs(pathX) == 1 && pathY == 0)
        ) {
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

