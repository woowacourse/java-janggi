package janggi.repository;

public class PieceInfo {

    private final String pieceType;
    private final String team;
    private final int rowValue;
    private final int columnValue;

    public PieceInfo(String pieceType, String team, int rowValue, int columnValue) {
        this.pieceType = pieceType;
        this.team = team;
        this.rowValue = rowValue;
        this.columnValue = columnValue;
    }

    public String getPieceType() {
        return pieceType;
    }

    public String getTeam() {
        return team;
    }

    public int getRowValue() {
        return rowValue;
    }

    public int getColumnValue() {
        return columnValue;
    }
}
