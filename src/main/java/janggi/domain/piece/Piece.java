package janggi.domain.piece;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public interface Piece {
    List<Point> getRoute(Point from, Point to);
    boolean canMove(List<Piece> route);
    boolean isSameTeam(Team team);
    boolean isSameType(PieceType type);
    PieceType getType();

    default boolean canCapture(Piece target) {
        return true;
    }
}
