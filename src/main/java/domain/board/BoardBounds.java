package domain.board;

import domain.coordinate.Position;
import java.util.ArrayList;
import java.util.List;

public final class BoardBounds {
    public static final BoardBounds JANGGI = new BoardBounds(10, 9);

    private final int colSize;
    private final int rowSize;

    public BoardBounds(int colSize, int rowSize) {
        this.colSize = colSize;
        this.rowSize = rowSize;
    }

    public boolean contains(Position position) {
        return position.col() >= 0 && position.col() < colSize
                && position.row() >= 0 && position.row() < rowSize;
    }

    public void validateContains(Position position) {
        if (!contains(position)) {
            throw new IllegalArgumentException(
                    String.format("잘못된 좌표: (%d, %d) (열 좌표는 0 에서 %d 사이, 행 좌표는 0 에서 %d 사이여야 합니다.)",
                            position.col(), position.row(), colSize - 1, rowSize - 1));
        }
    }

    public List<Position> allPositions() {
        List<Position> positions = new ArrayList<>();
        for (int col = 0; col < colSize; col++) {
            for (int row = 0; row < rowSize; row++) {
                positions.add(new Position(col, row));
            }
        }
        return positions;
    }

    public int colsize() {
        return colSize;
    }

    public int rowSize() {
        return rowSize;
    }
}
