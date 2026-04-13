package repository.entity;

public class PieceEntity {

    private final Long pieceId;
    private final Long gameId;
    private final String team;
    private final String pieceType;
    private final int boardRow;
    private final int boardColumn;

    public PieceEntity(
            final Long pieceId,
            final Long gameId,
            final String team,
            final String pieceType,
            final int boardRow,
            final int boardColumn
    ) {
        this.pieceId = pieceId;
        this.gameId = gameId;
        this.team = team;
        this.pieceType = pieceType;
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
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

    public int getBoardRow() {
        return boardRow;
    }

    public int getBoardColumn() {
        return boardColumn;
    }
}
