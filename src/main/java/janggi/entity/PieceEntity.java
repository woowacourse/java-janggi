package janggi.entity;

public class PieceEntity {
    private int gameId;
    private String type;
    private String team;
    private int row;
    private int column;

    public PieceEntity(int gameId, String type, String team, int row, int column) {
        this.gameId = gameId;
        this.type = type;
        this.team = team;
        this.row = row;
        this.column = column;
    }

    public int getGameId() {
        return gameId;
    }

    public String getType() {
        return type;
    }

    public String getTeam() {
        return team;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
