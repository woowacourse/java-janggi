package domain.pieces;

import static domain.pieces.PieceNames.EMPTY_PIECE;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.Point;
import execptions.JanggiArgumentException;
import java.util.List;

public final class EmptyPiece implements Piece {

  @Override
  public boolean hasEqualTeam(final Team team) {
    throw new JanggiArgumentException("기물이 없습니다.");
  }

  @Override
  public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
    throw new JanggiArgumentException("기물이 없습니다.");
  }

  @Override
  public boolean isMovable(final PieceOnRoute pieceOnRoute) {
    throw new JanggiArgumentException("기물이 없습니다.");
  }

  @Override
  public boolean canNotJumpOver() {
    return false;
  }

  @Override
  public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
    throw new JanggiArgumentException("기물이 없습니다.");
  }

  @Override
  public String getName() {
    return EMPTY_PIECE.getNameForTeam(Team.NONE);
  }

  @Override
  public boolean equals(final Object obj) {
    return obj instanceof EmptyPiece;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }


}
