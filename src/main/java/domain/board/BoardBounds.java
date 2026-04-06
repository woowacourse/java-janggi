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

    public boolean contains(int col, int row) {
        return col >= 0 && col < colSize && row >= 0 && row < rowSize;
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
