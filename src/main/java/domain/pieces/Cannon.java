package domain.pieces;

import static domain.pieces.PieceNames.CANNON;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.Point;
import domain.movements.PieceMovement;
import java.util.List;

public final class Cannon implements Piece {

  private static final int VALID_BETWEEN_PIECE_COUNT = 1;

  private final Team team;
  private final PieceMovement movements;

  public Cannon(final Team team, final PieceMovement pieceMovement) {
    this.movements = pieceMovement;
    this.team = team;
  }

  @Override
  public boolean hasEqualTeam(final Team team) {
    return this.team.equals(team);
  }

  @Override
  public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
    final List<Point> arrivalPoints = movements.calculateTotalArrivalPoints(startPoint);
    return arrivalPoints.contains(arrivalPoint);
  }


  @Override
  public boolean isMovable(final PieceOnRoute pieceOnRoute) {
    if (pieceOnRoute.countPieceOnRoute() != VALID_BETWEEN_PIECE_COUNT) {
      return false;
    }
    if (pieceOnRoute.canNotJumpOverFirstPiece()) {
      return false;
    }
    return !pieceOnRoute.hasArrivalPointInMyTeam(team);
  }

  @Override
  public boolean canNotJumpOver() {
    return true;
  }

  @Override
  public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
    return movements.calculateRoutePoints(startPoint, arrivalPoint);
  }

  @Override
  public String getName() {
    return CANNON.getNameForTeam(team);
  }

}
