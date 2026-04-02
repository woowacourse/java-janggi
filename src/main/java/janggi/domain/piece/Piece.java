package janggi.domain.piece;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public interface Piece {
    List<Point> getRoute(Point from, Point to);
    boolean canMove(List<Piece> route);
    boolean isSameTeam(Team team);
    PieceType getType();

    default boolean isSameType(PieceType type) {
        return getType() == type;
    }

    default boolean canCapture(Piece targetPiece) {
        return true;
    }

    default int getScore() {
        return getType().getScore();
    }
}
