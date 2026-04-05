package janggi.domain.board;

import janggi.domain.point.Point;

public class CastleZone {

    private final int minRow;
    private final int maxRow;
    private final int minColumn;
    private final int maxColumn;

    public CastleZone(Point topLeft, Point bottomRight) {
        this.minRow = bottomRight.getRow();
        this.maxRow = topLeft.getRow();
        this.minColumn = topLeft.getColumn();
        this.maxColumn = bottomRight.getColumn();
    }

    public boolean contains(Point target) {
        return target.getRow() >= minRow && target.getRow() <= maxRow &&
                target.getColumn() >= minColumn && target.getColumn() <= maxColumn;
    }
}
