package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Distance;
import domain.position.Point;
import java.util.List;

public class General extends AbstractPiece {

    public General(final Team team, final Score score) {
        super(team, score);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point prev, final Point newPoint) {
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
}
