package janggi.domain.piece;

import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;

public interface Piece {
    boolean canMove(Route route);
    boolean isSameTeam(Team team);
    boolean isSameType(PieceType type);
    Points getRoutePoints(Point from, Point to);
    PieceType getType();
    Team getTeam();
    int getScore();

    default boolean canCapture(Piece piece) {
        return true;
    }
}
