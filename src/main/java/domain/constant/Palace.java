package domain.constant;

import domain.Position;

public enum Palace {
    CHO(1, 3, 4, 6, Position.create(2, 5)),
    HAN(8, 10, 4, 6, Position.create(9, 5));

    private final int startRow;
    private final int endRow;
    private final int startCol;
    private final int endCol;
    private final Position center;

    Palace(int startRow, int endRow, int startCol, int endCol, Position position) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
        this.center = position;
    }

    public static Palace from(Country country) {
        if (country.equals(Country.CHO)) {
            return CHO;
        }
        return HAN;
    }

    public boolean isPalace(Position position) {
        return position.getRow() >= startRow
                && position.getRow() <= endRow && position.getCol() >= startCol && position.getCol() <= endCol;
    }

    public boolean isDiagonalPath(Position start, Position end) {
        if (!(isPalace(start) && isPalace(end))) {
            return false;
        }

        int diffRow = Math.abs(end.getRow() - start.getRow());
        int diffCol = Math.abs(end.getCol() - start.getCol());
        if (diffRow != diffCol) {
            return false;
        }

        if (!(isCorner(start) && isCorner(end))) {
            return false;
        }

        return diffRow == 1 || diffRow == 2;
    }

    private boolean isCorner(Position position) {
        return (position.getRow() == startRow || position.getRow() == endRow)
                && (position.getCol() == startCol || position.getCol() == endCol) || position.equals(center);
    }

    public Position getCenter() {
        return center;
    }
}
