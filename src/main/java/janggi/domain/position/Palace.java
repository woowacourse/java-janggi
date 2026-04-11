package janggi.domain.position;

import janggi.domain.Team;
import java.util.List;

public enum Palace {
    HAN(Position.of(1, 4), Position.of(3, 6), Position.of(2, 5)),
    CHO(Position.of(8, 4), Position.of(10, 6), Position.of(9, 5)),
    ;

    private final Position topLeft;
    private final Position bottomRight;
    private final Position center;

    Palace(Position topLeft, Position bottomRight, Position center) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.center = center;
    }

    public static Palace of(Team team) {
        if (team == Team.HAN) {
            return HAN;
        }
        return CHO;
    }

    public boolean contains(Position position) {
        int row = position.getRowValue();
        int col = position.getColumnValue();
        return row >= topLeft.getRowValue() && row <= bottomRight.getRowValue()
                && col >= topLeft.getColumnValue() && col <= bottomRight.getColumnValue();
    }

    public boolean isOnDiagonalLine(Position position) {
        if (!contains(position)) {
            return false;
        }
        return position.equals(center) || isCornerPosition(position);
    }

    private boolean isCornerPosition(Position position) {
        int row = position.getRowValue();
        int col = position.getColumnValue();
        return (row == topLeft.getRowValue() || row == bottomRight.getRowValue())
                && (col == topLeft.getColumnValue() || col == bottomRight.getColumnValue());
    }

    public boolean canMoveDiagonally(Position from, Position to) {
        int distance = from.rowDistanceTo(to);
        return distance > 0
                && distance == from.colDistanceTo(to)
                && isOnDiagonalLine(from)
                && isOnDiagonalLine(to);
    }

    private List<Position> diagonalPath(Position from, Position to) {
        if (from.rowDistanceTo(to) == 1) {
            return List.of();
        }
        return List.of(center);
    }

    public static boolean isDiagonalMove(Position from, Position to) {
        return HAN.canMoveDiagonally(from, to) || CHO.canMoveDiagonally(from, to);
    }

    public static List<Position> getDiagonalPath(Position from, Position to) {
        if (HAN.canMoveDiagonally(from, to)) {
            return HAN.diagonalPath(from, to);
        }
        return CHO.diagonalPath(from, to);
    }
}
