package domain.movements;

import domain.board.Point;
import java.util.List;

public interface PieceMovement {

    List<Point> calculateTotalArrivalPoints(Point startPoint);

    List<Point> calculateRoutePoints(Point startPoint, Point arrivalPoint);
}
