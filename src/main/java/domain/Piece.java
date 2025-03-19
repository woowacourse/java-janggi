package domain;

import java.util.List;

public interface Piece {
    boolean isMovable(final Distance distance);

    boolean isGreenTeam();

    List<Point> getPossiblePoint(final Point prev, final Point newPoint);
}
