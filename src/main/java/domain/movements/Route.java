package domain.movements;

import domain.board.BoardPoint;
import domain.board.Point;
import execptions.JanggiArgumentException;
import java.util.ArrayList;
import java.util.List;

public final class Route {
    private final List<Direction> directions;

    public Route(final List<Direction> directions) {
        this.directions = directions;
    }

    public Point navigateArrivalPoint(BoardPoint startBoardPoint) {
        Point point = startBoardPoint.toTempPoint();
        for (final Direction direction : directions) {
            point = point.move(direction);
        }
        return point;
    }

    public boolean canArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        final Point point = navigateArrivalPoint(startBoardPoint);
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
