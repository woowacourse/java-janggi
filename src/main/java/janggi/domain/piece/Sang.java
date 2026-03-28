package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.point.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Sang extends AbstractPiece {

    private static final int LONG_STEP = 3;
    private static final int SHORT_STEP = 2;

    public Sang(Team team) {
        super(team, PieceType.SANG);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.calculatePathColumn(from);
        int pathY = to.calculatePathRow(from);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);
        int signX = Integer.compare(pathX, 0);
        int signY = Integer.compare(pathY, 0);

        if (!((distanceX == LONG_STEP && distanceY == SHORT_STEP) ||
                (distanceX == SHORT_STEP && distanceY == LONG_STEP))) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        if (distanceX == LONG_STEP) {
            return List.of(
                    Point.of(from.getColumn() + (pathX / LONG_STEP), from.getRow()),
                    Point.of(from.getColumn() + signX * SHORT_STEP,  from.getRow() + signY)
            );
        }
        return List.of(
                Point.of(from.getColumn(), from.getRow() + (pathY / LONG_STEP)),
                Point.of(from.getColumn() + signX,  from.getRow() + signY * SHORT_STEP)
        );
    }
}
