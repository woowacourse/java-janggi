package domain.movements;

import domain.board.BoardPoint;
import domain.board.TempPoint;
import execptions.JanggiArgumentException;
import java.util.ArrayList;
import java.util.List;

public final class Route {
    private final List<Direction> directions;

    public Route(final List<Direction> directions) {
        this.directions = directions;
    }

    public TempPoint navigateArrivalPoint(BoardPoint startBoardPoint) {
        TempPoint tempPoint = startBoardPoint.toTempPoint();
        for (final Direction direction : directions) {
            tempPoint = tempPoint.move(direction);
        }
        return tempPoint;
    }

    public boolean canArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        final TempPoint point = navigateArrivalPoint(startBoardPoint);
        if (!point.isInRange()) {
            return false;
        }
        return point.toPoint().equals(arrivalBoardPoint);
    }

    public List<BoardPoint> getAllPointsOnRoute(BoardPoint boardPoint) {
        final List<BoardPoint> result = new ArrayList<>();
        try {
            for (final Direction direction : directions) {
                boardPoint = boardPoint.move(direction);
                result.add(boardPoint);
            }
            return result;
        } catch (JanggiArgumentException e) {
            return result;
        }
    }
}
