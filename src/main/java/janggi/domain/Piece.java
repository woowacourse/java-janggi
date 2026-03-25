package janggi.domain;

import java.util.List;

public interface Piece {
    boolean isSameTeam(Team team);
    List<Point> getRoute(Point from, Point to);
    boolean canMove(List<Piece> route);
}
