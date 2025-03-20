package domain.movements;

import domain.Point;
import java.util.List;

public interface PieceMovement {

    List<Point> calculateTotalArrivalPoints(Point startPoint);

    List<Point> calculateRoutePoints(Point startPoint, Point arrivalPoint);
}
