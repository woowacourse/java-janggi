package janggi.domain.piece.Implementation;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.direction.Offset;
import janggi.domain.piece.direction.SangDirection;
import janggi.domain.piece.template.AbstractFixedStepPiece;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Sang extends AbstractFixedStepPiece {

    private static final int SCORE = 3;

    public Sang(Team team) {
        super(SCORE, team, PieceType.SANG);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathCol = to.calculatePathColumn(from);
        int pathRow = to.calculatePathRow(from);

        SangDirection direction = SangDirection.find(pathCol, pathRow);

        List<Point> points = new ArrayList<>();
        for (Offset offset : direction.getWaypoints()) {
            points.add(Point.of(from.getColumn() + offset.directionColumn(), from.getRow() + offset.directionRow()));
        }

        return new Points(points);
    }
}
