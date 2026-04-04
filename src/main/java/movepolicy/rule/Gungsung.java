package movepolicy.rule;

import java.util.List;
import position.Column;
import position.Position;
import position.Row;

public class Gungsung {

    private static final Position CHO_LEFT_TOP = new Position(2, 3);
    private static final Position CHO_RIGHT_TOP = new Position(2, 5);
    private static final Position CHO_CENTER = new Position(1, 4);
    private static final Position CHO_LEFT_BOTTOM = new Position(0, 3);
    private static final Position CHO_RIGHT_BOTTOM = new Position(0, 5);

    private static final Position CHO_CENTER_TOP = new Position(2, 4);
    private static final Position CHO_LEFT_MIDDLE = new Position(1, 3);
    private static final Position CHO_RIGHT_MIDDLE = new Position(1, 5);
    private static final Position CHO_CENTER_BOTTOM = new Position(0, 4);

    private static final List<Position> DIAGONAL_MOVABLE_DEPARTURE = List.of(
        CHO_LEFT_TOP, CHO_RIGHT_TOP,
        CHO_CENTER,
        CHO_LEFT_BOTTOM, CHO_RIGHT_BOTTOM
    );

    private static final Row CHO_MIN_ROW = new Row(0);
    private static final Row CHO_MAX_ROW = new Row(2);
    private static final Column CHO_MIN_COLUMN = new Column(3);
    private static final Column CHO_MAX_COLUMN = new Column(5);

    public boolean isChoRange(Position position) {
        boolean isRowInRange = position.isRowInRange(CHO_MIN_ROW, CHO_MAX_ROW);
        boolean isColumnInRange = position.isColumnInRange(CHO_MIN_COLUMN, CHO_MAX_COLUMN);
        return isRowInRange && isColumnInRange;
    }

    public boolean isHanRange(Position position) {
        Position reversedPosition = position.reverse();
        boolean isRowInRange = reversedPosition.isRowInRange(CHO_MIN_ROW, CHO_MAX_ROW);
        boolean isColumnInRange = reversedPosition.isColumnInRange(CHO_MIN_COLUMN, CHO_MAX_COLUMN);
        return isRowInRange && isColumnInRange;
    }

    public boolean isDiagonalOneStepInside(Position departure, Position destination) {
        if (!isSameRange(departure, destination) || !isDiagonalMovableDeparture(departure)) {
            return false;
        }
        List<Position> movableOneStepDiagonals = departure.getMovableOneStepDiagonals();
        return movableOneStepDiagonals.contains(destination);
    }

    private boolean isDiagonalMovableDeparture(Position departure) {
        return DIAGONAL_MOVABLE_DEPARTURE.contains(departure)
            || DIAGONAL_MOVABLE_DEPARTURE.stream()
            .map(Position::reverse)
            .anyMatch(position -> position.equals(departure));
    }

    public boolean isSameRange(Position departure, Position destination) {
        return (isChoRange(departure) && isChoRange(destination))
            || (isHanRange(departure) && isHanRange(destination));
    }
}
