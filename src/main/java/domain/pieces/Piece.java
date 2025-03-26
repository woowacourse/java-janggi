package domain.pieces;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import java.util.List;

public interface Piece {

    boolean hasEqualTeam(Team team);

    boolean isAbleToArrive(Point start, Point arrival);

    boolean isMovableOnRoute(PiecesOnRoute piecesOnRoute);

    List<Point> getRoutePoints(Point start, Point arrival);

    String getName();

    default boolean canNotJumpOver() {
        return false;
    }

    default boolean canContinueWhenPieceRemove() {
        return true;
    }
}
