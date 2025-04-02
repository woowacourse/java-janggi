package domain.piece.coordiante;

import domain.piece.movement.Movement;
import java.util.List;

public record Coordinate(int row, int col) {

    private static final List<Coordinate> GUNG_CENTER = List.of(new Coordinate(2, 5), new Coordinate(9, 5));
    private static final List<Coordinate> GUNG_DOWN_RIGHT_CORNER = List.of(new Coordinate(3, 6), new Coordinate(8, 4));
    private static final List<Coordinate> GUNG_DOWN_LEFT_CORNER = List.of(new Coordinate(3, 4), new Coordinate(8, 6));
    private static final List<Coordinate> GUNG_UP_RIGHT_CORNER = List.of(new Coordinate(1, 6), new Coordinate(10, 4));
    private static final List<Coordinate> GUNG_UP_LEFT_CORNER = List.of(new Coordinate(1, 4), new Coordinate(10, 6));

    private static final int GUNG_COL_START = 4;
    private static final int GUNG_COL_END = 6;

    private static final int HAN_GUNG_ROW_START = 1;
    private static final int HAN_GUNG_ROW_END = 3;

    private static final int CHO_GUNG_ROW_START = 8;
    private static final int CHO_GUNG_ROW_END = 10;

    public static final int MAX_ROW = 10;
    public static final int MAX_COL = 9;

    public boolean isInBoundary() {
        return !isOutOfBoundary();
    }

    public boolean isOutOfBoundary() {
        if (this.row < 1 || this.row > MAX_ROW) {
            return true;
        }
        return this.col < 1 || this.col > MAX_COL;
    }

    public Coordinate move(int increaseRow, int increaseCol) {
        return new Coordinate(row + increaseRow, col + increaseCol);
    }

    public Coordinate move(Movement movement) {
        return move(movement.getRow(), movement.getCol());
    }

    public boolean isGungDownRightCorner() {
        return GUNG_DOWN_RIGHT_CORNER.contains(this);
    }

    public boolean isGungDownLeftCorner() {
        return GUNG_DOWN_LEFT_CORNER.contains(this);
    }

    public boolean isGungUpRightCorner() {
        return GUNG_UP_RIGHT_CORNER.contains(this);
    }

    public boolean isGungUpLeftCorner() {
        return GUNG_UP_LEFT_CORNER.contains(this);
    }

    public boolean isGungCenter() {
        return GUNG_CENTER.contains(this);
    }

    public boolean isInGungBoundary() {
        return isInGungColumnRange() && (isInHanGungRowRange() || isInChoGungRowRange());
    }

    private boolean isInGungColumnRange() {
        return col >= GUNG_COL_START && col <= GUNG_COL_END;
    }

    private boolean isInHanGungRowRange() {
        return row >= HAN_GUNG_ROW_START && row <= HAN_GUNG_ROW_END;
    }

    private boolean isInChoGungRowRange() {
        return row >= CHO_GUNG_ROW_START && row <= CHO_GUNG_ROW_END;
    }

}
