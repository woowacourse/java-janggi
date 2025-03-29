package janggi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Position(int row, int column) {

    public static final int MIN_ROW = 1;
    public static final int MIN_COLUMN = 1;
    public static final int MAX_ROW = 10;
    public static final int MAX_COLUMN = 9;

    public Position {
        if (isInValidPosition(row, column)){
            throw new IllegalArgumentException("위치는 장기판 내부여야 합니다.");
        }
    }

    public boolean canMove(final Direction direction) {
        return !isInValidPosition(row + direction.deltaRow(), column + direction.deltaColumn());
    }

    public boolean canMove(final Directions directions) {
        int deltaRow = row + directions.calculateTotalDeltaRow();
        int deltaColumn = column + directions.calculateTotalDeltaColumn();
        return !isInValidPosition(deltaRow, deltaColumn);
    }

    public boolean isDestinationCross(Position destinationPosition) {
        return row() != destinationPosition.row() && column() != destinationPosition.column();
    }

    public boolean isInCastle() {
        return Arrays.stream(CastleArea.values()).anyMatch(castleArea -> castleArea.getPosition().equals(this));
    }

    public Position move(final Direction direction) {
        return new Position(row + direction.deltaRow(), column + direction.deltaColumn());
    }

    public PositionsInDirection getPositionsInDirection(Direction direction) {
        List<Position> positions = new ArrayList<>();
        Position currentPosition = this;
        while (currentPosition.canMove(direction)) {
            currentPosition = currentPosition.move(direction);
            positions.add(currentPosition);
        }
        return new PositionsInDirection(positions);
    }

    private boolean isInValidPosition(int row, int column) {
        return row < MIN_ROW || column < MIN_COLUMN || row > MAX_ROW || column > MAX_COLUMN;
    }
}
