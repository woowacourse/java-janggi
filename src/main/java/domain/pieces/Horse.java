package domain.pieces;

import static domain.pieces.PieceNames.HORSE;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.Point;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import java.util.List;

public final class Horse implements Piece {

  private final Team team;
  private final PieceMovement movement;

  public Horse(final Team team) {
    this.team = team;
    this.movement = getDefaultMovementForHorse();
  }

  public Horse(final Team team, final PieceMovement movement) {
    this.team = team;
    this.movement = movement;
  }

  @Override
  public boolean hasEqualTeam(final Team team) {
    return this.team.equals(team);
  }

  @Override
  public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
    return movement.calculateTotalArrivalPoints(startPoint).contains(arrivalPoint);
  }

  @Override
  public boolean isMovable(final PieceOnRoute pieceOnRoute) {
    if (pieceOnRoute.hasArrivalPointInMyTeam(team)) {
      return false;
    }
    return pieceOnRoute.hasNotPieceOnRoute();
  }

  @Override
  public boolean canNotJumpOver() {
    return false;
  }

  @Override
  public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
    return movement.calculateRoutePoints(startPoint, arrivalPoint);
  }

  @Override
  public String getName() {
    return HORSE.getNameForTeam(team);
  }

  private DefaultMovement getDefaultMovementForHorse() {
    return new DefaultMovement(List.of(
        new Route(List.of(Direction.NORTH, Direction.NORTHWEST)),
        new Route(List.of(Direction.NORTH, Direction.NORTHEAST)),
        new Route(List.of(Direction.EAST, Direction.NORTHEAST)),
        new Route(List.of(Direction.EAST, Direction.SOUTHEAST)),
        new Route(List.of(Direction.SOUTH, Direction.SOUTHEAST)),
        new Route(List.of(Direction.SOUTH, Direction.SOUTHWEST)),
        new Route(List.of(Direction.WEST, Direction.SOUTHWEST)),
        new Route(List.of(Direction.WEST, Direction.NORTHWEST))
    ));
  }
}
