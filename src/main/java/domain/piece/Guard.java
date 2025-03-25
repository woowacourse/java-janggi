package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Distance;
import domain.position.Point;
import java.util.List;

public class Guard extends AbstractPiece {

    public Guard(final Team team) {
        super(team, Score.GUARD);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        return List.of();
    }

    @Override
    public boolean isMovable(final Distance distance) {
        final int absoluteX = Math.abs(distance.x());
        final int absoluteY = Math.abs(distance.y());
        if (absoluteX == 1 && absoluteY == 0) {
            return true;
        }
        return absoluteX == 0 && absoluteY == 1;
    }

    @Override
    public PieceType type() {
        return PieceType.GUARD;
    }
}
