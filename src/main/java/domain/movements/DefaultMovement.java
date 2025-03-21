package domain.movements;

import domain.board.Point;
import execptions.JanggiArgumentException;
import java.util.List;

public final class DefaultMovement implements PieceMovement {

  private final List<Route> routes;

  public DefaultMovement(final List<Route> routes) {
    this.routes = routes;
  }

  @Override
  public List<Point> calculateTotalArrivalPoints(final Point startPoint) {
    return routes.stream()
        .map(route -> route.navigateArrivalPoint(startPoint))
        .toList();
  }

  @Override
  public List<Point> calculateRoutePoints(final Point startPoint, final Point arrivalPoint) {
    for (final Route route : routes) {
      if (route.canArrive(startPoint, arrivalPoint)) {
        return route.getAllPointsOnRoute(startPoint);
      }
    }
    throw new JanggiArgumentException("해당 도착점으로 도착할 수 없는 기물입니다.");
  }
}
