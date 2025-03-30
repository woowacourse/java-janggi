package domain.pieces;

import domain.Team;
import domain.board.BoardPoint;
import domain.board.PieceOnRoute;
import java.util.List;

public interface Piece {

    boolean hasEqualTeam(Team team);

    boolean isAbleToArrive(BoardPoint startBoardPoint, BoardPoint arrivalBoardPoint);

    List<BoardPoint> getRoutePoints(BoardPoint startBoardPoint, BoardPoint arrivalBoardPoint);

    boolean isMovable(PieceOnRoute pieceOnRoute);

    boolean canNotJumpOver();

    String getName();

    int getScore();
}
