package domain.movements;

import domain.board.BoardPoint;
import java.util.List;

public interface PieceMovement {

    List<BoardPoint> calculateTotalArrivalPoints(BoardPoint startBoardPoint);

    List<BoardPoint> calculateRoutePoints(BoardPoint startBoardPoint, BoardPoint arrivalBoardPoint);
}
