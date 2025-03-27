package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Direction;
import domain.position.Point;
import java.util.List;

public class General extends AbstractPiece {

    public General(final Team team) {
        super(team, Score.GENERAL);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        return List.of();
    }

    @Override
    public boolean isMovable(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);
        final int absoluteX = Math.abs(direction.x());
        final int absoluteY = Math.abs(direction.y());
        if (absoluteX == 1 && absoluteY == 0) {
            return true;
        }
        return absoluteX == 0 && absoluteY == 1;
    }

    @Override
    public PieceType type() {
        return PieceType.GENERAL;
    }
}
