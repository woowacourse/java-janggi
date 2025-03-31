package domain.janggi.domain;

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

    public boolean canMove(final List<Direction> directions) {
        int deltaRow = directions.stream().mapToInt(Direction::deltaRow).sum();
        int deltaColumn = directions.stream().mapToInt(Direction::deltaColumn).sum();
        return !isInValidPosition(row + deltaRow, column + deltaColumn);
    }

    public Position moveByDirection(final Direction direction) {
        return new Position(row + direction.deltaRow(), column + direction.deltaColumn());
    }

    public Position moveByPath(final Path path) {
        return path.targetPosition();
    }

    private boolean isInValidPosition(int row, int column) {
        return row < MIN_ROW || column < MIN_COLUMN || row > MAX_ROW || column > MAX_COLUMN;
    }
}
