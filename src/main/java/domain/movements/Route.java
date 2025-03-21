package domain.movements;

import domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public final class Route {

  private final List<Direction> directions;

  public Route(final List<Direction> directions) {
    this.directions = directions;
  }

  public Point navigateArrivalPoint(Point point) {
    for (final Direction direction : directions) {
      point = point.move(direction);
    }
    return point;
  }

  public boolean canArrive(final Point startPoint, final Point arrivalPoint) {
    final Point point = navigateArrivalPoint(startPoint);
    return point.equals(arrivalPoint);
  }

  public List<Point> getAllPointsOnRoute(Point point) {
    final List<Point> result = new ArrayList<>();
    for (final Direction direction : directions) {
      point = point.move(direction);
      result.add(point);
    }
    return result;
  }
}
