package movepolicy.rule;

import java.util.List;
import pieces.Side;
import position.Column;
import position.Position;
import position.Row;

public class Gungsung {

    private static final Position CHO_LEFT_TOP = new Position(2, 3);
    private static final Position CHO_RIGHT_TOP = new Position(2, 5);
    private static final Position CHO_CENTER = new Position(1, 4);
    private static final Position CHO_LEFT_BOTTOM = new Position(0, 3);
    private static final Position CHO_RIGHT_BOTTOM = new Position(0, 5);

    private static final List<Position> CHO_DIAGONAL_POINTS = List.of(
        CHO_LEFT_TOP, CHO_RIGHT_TOP,
        CHO_CENTER,
        CHO_LEFT_BOTTOM, CHO_RIGHT_BOTTOM
    );
    private static final List<Position> HAN_DIAGONAL_POINTS = CHO_DIAGONAL_POINTS.stream()
        .map(Position::reverse)
        .toList();

    private static final Row CHO_MIN_ROW = new Row(0);
    private static final Row CHO_MAX_ROW = new Row(2);
    private static final Column CHO_MIN_COLUMN = new Column(3);
    private static final Column CHO_MAX_COLUMN = new Column(5);

    public boolean isChoRange(Position position) {
        return position.isRowInRange(CHO_MIN_ROW, CHO_MAX_ROW)
            && position.isColumnInRange(CHO_MIN_COLUMN, CHO_MAX_COLUMN);
    }

    public boolean isHanRange(Position position) {
        return isChoRange(position.reverse());
    }

    public boolean isOneStepDiagonalInside(Position departure, Position destination) {
        if (!isDiagonalReachableRange(departure, destination)) {
            return false;
        }
        return departure.getMovableOneStepDiagonals().contains(destination);
    }

    public boolean isDiagonalInside(Position departure, Position destination) {
        if (!isDiagonalReachableRange(departure, destination)) {
            return false;
        }
        return departure.calculateDeltaTo(destination).isDiagonal();
    }

    private boolean isDiagonalReachableRange(Position departure, Position destination) {
        return isInsideSameGungsung(departure, destination)
            && isDepartureDiagonalMovable(departure);
    }

    private boolean isDepartureDiagonalMovable(Position departure) {
        return CHO_DIAGONAL_POINTS.contains(departure)
            || HAN_DIAGONAL_POINTS.contains(departure);
    }

    public boolean isInsideSameGungsung(Position departure, Position destination) {
        return (isChoRange(departure) && isChoRange(destination))
            || (isHanRange(departure) && isHanRange(destination));
    }

    public Position getCenterPosition(Side side) {
        if (side.isCho()) {
            return CHO_CENTER;
        }
        return CHO_CENTER.reverse();
    }
}
