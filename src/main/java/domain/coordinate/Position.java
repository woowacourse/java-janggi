package domain.coordinate;

import domain.board.BoardBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Position(int col, int row) {
    private static final BoardBounds bounds = BoardBounds.JANGGI;

    public Position {
        validate(col, row);
    }

    public Optional<Position> tryNextPosition(Direction direction) {
        return tryCreate(col + direction.getCol(), row + direction.getRow());
    }

    private Optional<Position> tryCreate(int col, int row) {
        if (!bounds.contains(col, row)) {
            return Optional.empty();
        }
        return Optional.of(new Position(col, row));
    }

    public List<Position> rayPositions(Direction direction) {
        List<Position> positions = new ArrayList<>();
        Optional<Position> next = tryNextPosition(direction);
        while (next.isPresent()) {
            positions.add(next.get());
            next = next.get().tryNextPosition(direction);
        }
        return positions;
    }

    private void validate(int col, int row) {
        if (!bounds.contains(col, row)) {
            throw new IllegalArgumentException(
                    String.format("잘못된 좌표: (%d, %d) (열 좌표는 0 에서 %d 사이, 행 좌표는 0 에서 %d 사이여야 합니다.)",
                            col, row, bounds.colsize() - 1, bounds.rowSize() - 1));
        }
    }
}
