package domain.pieces;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import java.util.List;

public interface Piece {

    boolean hasEqualTeam(Team team);

    boolean isAbleToArrive(Point startPoint, Point arrivalPoint);

    List<Point> getRoutePoints(Point startPoint, Point arrivalPoint);

    boolean isMovable(PieceOnRoute pieceOnRoute);

    boolean canNotJumpOver();
}
