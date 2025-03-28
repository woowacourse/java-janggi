package domain.movements;

import domain.board.Point;
import java.util.List;

public interface PieceMovement {

    List<Point> searchTotalArrivalPoints(Point start);

    List<Point> calculatePointsOnRoute(Point start, Point arrival);
}
