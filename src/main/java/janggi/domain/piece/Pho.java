package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.piece.direction.CastleDirection;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Pho extends AbstractPiece {

    private static final int SCORE = 7;

    public Pho(Team team) {
        super(SCORE, team, PieceType.PHO);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathCol = to.calculatePathColumn(from);
        int pathRow = to.calculatePathRow(from);
        int signCol = Integer.compare(pathCol, 0);
        int signRow = Integer.compare(pathRow, 0);

        validateDirection(from, to, signCol, signRow, pathCol, pathRow);

        int distance = Math.max(abs(pathCol), abs(pathRow));

        List<Point> points = new ArrayList<>();
        for (int i = 1; i < distance; i++) {
            int nextCol = from.getColumn() + (signCol * i);
            int nextRow = from.getRow() + (signRow * i);
            points.add(Point.of(nextCol, nextRow));
        }
        return new Points(points);
    }

    @Override
    public boolean canMove(Route route) {
        if (route.hasObstacle()) {
            return false;
        }
        return !route.hasSameType(PieceType.PHO);
    }

    @Override
    public boolean canCapture(Piece target) {
        return !target.isSameType(PieceType.PHO);
    }

    private void validateDirection(Point from, Point to, int signCol, int signRow, int pathCol, int pathRow) {
        if (from.inSameCastle(to)) {
            CastleDirection.find(from, signCol, signRow);
            return;
        }

        if (pathCol != 0 && pathRow !=0) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
    }
}
