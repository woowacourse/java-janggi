package domain.coordinate;

import domain.board.BoardBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Position(int row, int col) {
    private static final BoardBounds bounds = BoardBounds.JANGGI;

    public Position {
        validate(row, col);
    }

    public Optional<Position> tryNextPosition(Direction direction) {
        return tryCreate(row + direction.getRow(), col + direction.getCol());
    }

    public boolean isInPalace() {
        return bounds.isInPalace(row, col);
    }

    private Optional<Position> tryCreate(int row, int col) {
        if (!bounds.contains(row, col)) {
            return Optional.empty();
        }
        return Optional.of(new Position(row, col));
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

    private void validate(int row, int col) {
        if (!bounds.contains(row, col)) {
            throw new IllegalArgumentException(
                    String.format("잘못된 좌표: (%d, %d) (행 좌표는 0 에서 %d 사이, 열 좌표는 0 에서 %d 사이여야 합니다.)",
                            row, col, bounds.rowSize() - 1, bounds.colSize() - 1));
        }
    }
}
