package domain;

import java.util.ArrayList;
import java.util.List;

public class Directions {
    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public boolean canApplyFrom(final ChessPosition startPosition) {
        ChessPosition currentPosition = startPosition;
        for (Direction direction : directions) {
            final int nextRow = currentPosition.row() + direction.dr;
            final int nextCol = currentPosition.column() + direction.dc;
            if (!ChessPosition.isValid(nextRow, nextCol)) {
                return false;
            }
            currentPosition = new ChessPosition(nextRow, nextCol);
        }
        return true;
    }

    public Path getPathFrom(ChessPosition currentPosition) {
        List<ChessPosition> positions = new ArrayList<>();
        for (Direction direction : directions) {
            positions.add(currentPosition);
            final int nextRow = currentPosition.row() + direction.dr;
            final int nextCol = currentPosition.column() + direction.dc;
            validatePosition(nextRow, nextCol);
            currentPosition = new ChessPosition(nextRow, nextCol);
        }
        positions.add(currentPosition);
        return new Path(positions);
    }

    private void validatePosition(final int row, final int col) {
        if (!ChessPosition.isValid(row, col)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }
}
