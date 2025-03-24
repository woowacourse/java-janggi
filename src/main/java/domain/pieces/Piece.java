package domain.pieces;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import java.util.List;

public interface Piece {

    boolean hasEqualTeam(Team team);

    boolean isAbleToArrive(Point start, Point arrival);

    boolean isMovable(PiecesOnRoute pieces);

    boolean canNotJumpOver();

    List<Point> getRoutePoints(Point start, Point arrival);

    String getName();
}
