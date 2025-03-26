package domain.piece;

import domain.position.Point;
import java.util.List;

public interface Piece {
    boolean isMovable(final Point fromPoint, final Point toPoint);

    boolean isGreenTeam();

    List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint);

    PieceType type();
}
