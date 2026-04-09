package domain.board;

public record PalaceBounds(int startRow, int endRow, int startCol, int endCol) {

    public boolean contains(int row, int col) {
        return row >= startRow && row <= endRow && col >= startCol && col <= endCol;
    }

    public PalaceBounds mirror(int rowSize) {
        int newStartRow = rowSize - 1 - endRow;
        int newEndRow = rowSize - 1 - startRow;
        return new PalaceBounds(newStartRow, newEndRow, startCol, endCol);
    }
}
