package janggi.domain.piece;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public interface Piece {
    boolean isSameTeam(Team team);
    List<Point> getRoute(Point from, Point to);
    boolean canMove(List<Piece> route);
    boolean isSameType(PieceType type);
}
