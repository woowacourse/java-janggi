package domain.board;

import domain.coordinate.Position;
import java.util.ArrayList;
import java.util.List;

public final class BoardBounds {
    public static final BoardBounds JANGGI = createJanggi();

    private final int rowSize;
    private final int colSize;
    private final List<PalaceBounds> palaces;

    private static BoardBounds createJanggi() {
        PalaceBounds palace = new PalaceBounds(0, 2, 3, 5);
        return new BoardBounds(10, 9, List.of(palace, palace.mirror(10)));
    }

    public BoardBounds(int rowSize, int colSize, List<PalaceBounds> palaces) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.palaces = palaces;
    }

    public boolean contains(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }

    public boolean isInPalace(int row, int col) {
        for (PalaceBounds palace : palaces) {
            if (palace.contains(row, col)) {
                return true;
            }
        }
        return false;
    }

    public List<Position> allPositions() {
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                positions.add(new Position(row, col));
            }
        }
        return positions;
    }

    public int rowSize() {
        return rowSize;
    }

    public int colSize() {
        return colSize;
    }

    public List<PalaceBounds> getPalaces() {
        return palaces;
    }
}
