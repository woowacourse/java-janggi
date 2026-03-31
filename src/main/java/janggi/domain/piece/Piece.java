package janggi.domain.piece;

import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;

public interface Piece {
    boolean canMove(Route route);
    boolean isSameTeam(Team team);
    boolean isSameType(PieceType type);
    boolean canCapture(Piece target);
    Points getRoutePoints(Point from, Point to);
    PieceType getType();
    int getScore();
}
