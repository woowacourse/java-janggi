package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Jol extends BasePiece {

    private static final int MAX_DISTANCE = 1;

    public Jol(Team team) {
        super(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getX() - from.getX();
        int pathY = to.getY() - from.getY();

        int signY = Integer.compare(pathY, 0);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if (distanceX > MAX_DISTANCE || distanceY > MAX_DISTANCE || (distanceX + distanceY > MAX_DISTANCE)) {
            throw new IllegalArgumentException();
        }
        if ((team.equals(Team.CHO) && signY < 0) || (team.equals(Team.HAN) && signY > 0)) {
            throw new IllegalArgumentException();
        }
        return List.of(to);
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.stream()
                .noneMatch(piece -> piece.isSameTeam(team));
    }

    @Override
    public PieceType getType() {
        return PieceType.JOL;
    }
}

