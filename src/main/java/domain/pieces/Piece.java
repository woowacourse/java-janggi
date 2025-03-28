package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.player.Score;
import domain.player.TeamType;
import java.util.List;

public interface Piece {

    boolean hasEqualTeam(TeamType teamType);

    boolean isAbleToArrive(Point start, Point arrival);

    boolean isMovableOnRoute(PiecesOnRoute piecesOnRoute);

    List<Point> searchRoutePoints(Point start, Point arrival);

    String getName();

    Score getScore();

    default boolean canNotJumpOver() {
        return false;
    }

    default boolean canContinueWhenThisRemove() {
        return true;
    }

    default Piece inRangeOfPalace() {
        return this;
    }
}
