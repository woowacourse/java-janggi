package domain.piece;

import domain.Score;
import domain.position.Distance;
import domain.position.Point;
import java.util.List;

public interface Piece {
    boolean isMovable(final Distance distance);

    boolean isGreenTeam();

    List<Point> getPossiblePoint(final Point prev, final Point newPoint);

    Score getScore();

    boolean equals(final Object other);

    int hashCode();
}
