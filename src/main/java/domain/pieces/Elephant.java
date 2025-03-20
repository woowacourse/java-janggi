package domain.pieces;

import static domain.pieces.PieceNames.ELEPHANT;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.Point;
import domain.movements.PieceMovement;
import java.util.List;

public final class Elephant implements Piece {

  private final Team team;
  private final PieceMovement defaultMovement;

  public Elephant(final Team team, final PieceMovement defaultMovement) {
    this.defaultMovement = defaultMovement;
    this.team = team;
  }

  @Override
  public boolean hasEqualTeam(final Team team) {
    return this.team.equals(team);
  }

  @Override
  public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
    return defaultMovement.calculateTotalArrivalPoints(startPoint).contains(arrivalPoint);
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
    return defaultMovement.calculateRoutePoints(startPoint, arrivalPoint);
  }

  @Override
  public String getName() {
    return ELEPHANT.getNameForTeam(team);
  }
}
