package janggi.domain.piece;

import janggi.domain.piece.direction.SangDirection;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.status.Team;
import java.util.List;

public class Sang extends AbstractPiece {

    public Sang(Team team) {
        super(team, PieceType.SANG);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathCol = to.calculatePathColumn(from);
        int pathRow = to.calculatePathRow(from);

        SangDirection direction = SangDirection.find(pathCol, pathRow);

        Point routePoint1 = Point.of(
                from.getColumn() + direction.getRoute1Col(),
                from.getRow() + direction.getRoute1Row()
        );
        Point routePoint2 = Point.of(from.getColumn() + direction.getRoute2Col(),
                from.getRow() + direction.getRoute2Row()
        );

        return new Points(List.of(routePoint1, routePoint2));
    }
}
