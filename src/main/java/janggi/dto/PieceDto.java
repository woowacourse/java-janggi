package janggi.dto;

public class PieceDto {
    private final int row;
    private final int column;
    private final String pieceName;

    public PieceDto(int row, int column, String pieceName) {
        this.row = row;
        this.column = column;
        this.pieceName = pieceName;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getName() {
        return pieceName;
    }
}
