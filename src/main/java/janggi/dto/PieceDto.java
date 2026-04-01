package janggi.dto;

public class PieceDto {
    private final int row;
    private final int column;
    private final String pieceName;
    private final String team;

    public PieceDto(int row, int column, String pieceName, String team) {
        this.row = row;
        this.column = column;
        this.pieceName = pieceName;
        this.team = team;
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

    public String getTeam() {
        return team;
    }
}
