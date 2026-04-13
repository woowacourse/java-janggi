package repository.entity;

public class PieceEntity {

    private final Long pieceId;
    private final Long gameId;
    private final String team;
    private final String pieceType;
    private final int row;
    private final int column;

    public PieceEntity(
            final Long pieceId,
            final Long gameId,
            final String team,
            final String pieceType,
            final int row,
            final int column
    ) {
        this.pieceId = pieceId;
        this.gameId = gameId;
        this.team = team;
        this.pieceType = pieceType;
        this.row = row;
        this.column = column;
    }


    public Long getPieceId() {
        return pieceId;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getTeam() {
        return team;
    }

    public String getPieceType() {
        return pieceType;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
