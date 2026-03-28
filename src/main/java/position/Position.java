package position;

import pieces.Side;

public record Position(Row row, Column column) {

    public Position(int row, int column) {
        this(new Row(row), new Column(column));
    }

    public Position moveForward(Side side) {
        if (side.isCho()) {
            return new Position(row.up(), column);
        }
        return new Position(row.down(), column);
    }

    public Position moveBack(Side side) {
        if (side.isCho()) {
            return new Position(row.down(), column);
        }
        return new Position(row.up(), column);
    }

    public Position moveRight(Side side) {
        if (side.isCho()) {
            return new Position(row, column.right());
        }
        return new Position(row, column.left());
    }

    public Position moveLeft(Side side) {
        if (side.isCho()) {
            return new Position(row, column.left());
        }
        return new Position(row, column.right());
    }

    public Position moveRightForward(Side side) {
        if (side.isCho()) {
            return new Position(row.up(), column.right());
        }
        return new Position(row.down(), column.left());
    }

    public Position moveLeftForward(Side side) {
        if (side.isCho()) {
            return new Position(row.up(), column.left());
        }
        return new Position(row.down(), column.right());
    }

    public Position moveRightBack(Side side) {
        if (side.isCho()) {
            return new Position(row.down(), column.right());
        }
        return new Position(row.up(), column.left());
    }

    public Position moveLeftBack(Side side) {
        if (side.isCho()) {
            return new Position(row.down(), column.left());
        }
        return new Position(row.up(), column.right());
    }

    public boolean isSameRow(Position departure) {
        return this.row.equals(departure.row);
    }

    public boolean isSameColumn(Position departure) {
        return this.column.equals(departure.column);
    }

    public boolean isBackRow(Position destination, Side side) {
        if (side.isCho()) {
            return this.row.isLowerThan(destination.row);
        }
        return this.row.isBiggerThan(destination.row);
    }

    public boolean isLeftColumn(Position destination, Side side) {
        if (side.isCho()) {
            return this.column.isLeft(destination.column);
        }
        return this.column.isRight(destination.column);
    }

    public boolean isGapBiggerThanOne(Position destination) {
        return row.isGapBiggerThanOne(destination.row) ||
            column.isGapBiggerThanOne(destination.column);
    }
}
