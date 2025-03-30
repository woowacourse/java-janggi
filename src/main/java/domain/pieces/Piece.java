package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.player.Score;
import domain.player.Team;
import java.util.List;

public interface Piece {

    boolean hasEqualTeam(Team team);

    boolean isAbleToArrive(Point start, Point arrival);

    boolean isMovableOnRoute(PiecesOnRoute piecesOnRoute);

    List<Point> searchRoutePoints(Point start, Point arrival);

    String getName();

    Score getScore();

    PieceDefinition getType();

    int getPlayerId();

    default boolean canNotJumpOver() {
        return false;
    }

    default boolean canContinueGameAfterRemoval() {
        return true;
    }

    default Piece inRangeOfPalace() {
        return this;
    }
}
