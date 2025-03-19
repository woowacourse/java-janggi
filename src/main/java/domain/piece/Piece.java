package domain.piece;

import domain.Point;
import domain.Team;
import java.util.List;

public interface Piece {

    boolean hasEqualTeam(Team team);

    List<Point> getArrivalPoint(Point startPoint);

    List<Point> getRoutePoints(Point startPoint, Point arrivalPoint);

    boolean isMovable(PieceOnRoute pieceOnRoute);
}
