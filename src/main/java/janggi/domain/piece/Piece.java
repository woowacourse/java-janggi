package janggi.domain.piece;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public interface Piece {
    List<Point> getRoute(Point from, Point to);
    PieceType getType();
    boolean canMove(List<Piece> route);
    boolean isSameTeam(Team team);
    boolean isSameType(PieceType type);
    boolean canCapture(Piece target);
}
