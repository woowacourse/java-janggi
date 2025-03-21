package domain.pieces;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.Point;
import java.util.List;

public interface Piece {

  boolean hasEqualTeam(Team team);

  boolean isAbleToArrive(Point startPoint, Point arrivalPoint);

  boolean isMovable(PieceOnRoute pieceOnRoute);

  boolean canNotJumpOver();

  List<Point> getRoutePoints(Point startPoint, Point arrivalPoint);

  String getName();
}
