package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Sa extends BasePiece {

    private static final int MAX_DISTANCE = 1;

    public Sa(Team team) {
        super(team);
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getX() - from.getX();
        int pathY = to.getY() - from.getY();
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if ( distanceX > MAX_DISTANCE || distanceY > MAX_DISTANCE || (distanceX == 0 && distanceY == 0)) {
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
        return PieceType.SA;
    }
}
