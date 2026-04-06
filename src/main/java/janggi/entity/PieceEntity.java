package janggi.entity;

public class PieceEntity {
    private int gameId;
    private String type;
    private int row;
    private int column;

    public PieceEntity(int gameId, String type, int row, int column) {
        this.gameId = gameId;
        this.type = type;
        this.row = row;
        this.column = column;
    }

    public int getGameId() {
        return gameId;
    }

    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
