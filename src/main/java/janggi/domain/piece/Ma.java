package janggi.domain.piece;

import janggi.domain.piece.direction.MaDirection;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.status.Team;
import java.util.List;

public class Ma extends Piece {

    private static final int SCORE = 5;

    public Ma(Team team) {
        super(SCORE, team, PieceType.MA);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathX = to.calculatePathColumn(from);
        int pathY = to.calculatePathRow(from);

        MaDirection direction = MaDirection.find(pathX, pathY);
        Point point = Point.of(from.getColumn() + direction.getRouteCol(), from.getRow() + direction.getRouteRow());
        return new Points(List.of(point));
    }
}
