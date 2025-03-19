package domain.piece;

import domain.Point;
import domain.Team;
import java.util.List;

public interface Piece {

    public boolean hasEqualTeam(Team team);

    public List<Point> getArrivalPoint(Point startPoint);

    public List<Point> getRoutePoints(Point startPoint, Point arrivalPoint);

    public boolean isMovable(PieceOnRoute pieceOnRoute);
}
