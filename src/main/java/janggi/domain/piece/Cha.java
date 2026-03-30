package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.piece.direction.CastleDirection;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Cha extends AbstractPiece {

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathCol = to.calculatePathColumn(from);
        int pathRow = to.calculatePathRow(from);
        int signCol = Integer.compare(pathCol, 0);
        int signRow = Integer.compare(pathRow, 0);
        List<Point> points = new ArrayList<>();
        if (from.inSameCastle(to)) {
            CastleDirection castleDirection = CastleDirection.find(from, signCol, signRow);
            points.add(Point.of(
                    from.getColumn() + castleDirection.getTargetCol(),
                    from.getRow() + castleDirection.getTargetRow()
                    )
            );
            return new Points(points);
        }
        if (pathRow != 0 && pathCol != 0) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        int distance = Math.max(abs(pathCol), abs(pathRow));
        for (int i = 1; i < distance; i++) {
            int nextCol = from.getColumn() + (signCol * i);
            int nextRow = from.getRow() + (signRow * i);
            points.add(Point.of(nextCol, nextRow));
        }
        return new Points(points);
    }
}
