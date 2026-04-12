package infra.entity;

public class CurrentPiecePositionEntity {
    private final Long id;
    private final Long gameId;
    private final String pieceType;
    private final String pieceTeam;
    private final int boardRow;
    private final int boardColumn;

    private CurrentPiecePositionEntity(Long id, Long gameId, String pieceType, String pieceTeam, int boardRow,
                                       int boardColumn) {
        this.id = id;
        this.gameId = gameId;
        this.pieceType = pieceType;
        this.pieceTeam = pieceTeam;
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
    }

    public static CurrentPiecePositionEntity createWithoutId(Long gameId, String pieceType, String pieceTeam,
                                                             int boardRow, int boardColumn) {
        return new CurrentPiecePositionEntity(null, gameId, pieceType, pieceTeam, boardRow, boardColumn);
    }

    public static CurrentPiecePositionEntity createWithId(Long id, Long gameId, String pieceType, String pieceTeam,
                                                          int boardRow, int boardColumn) {
        return new CurrentPiecePositionEntity(id, gameId, pieceType, pieceTeam, boardRow, boardColumn);
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getPieceType() {
        return pieceType;
    }

    public String getPieceTeam() {
        return pieceTeam;
    }

    public int getBoardRow() {
        return boardRow;
    }

    public int getBoardColumn() {
        return boardColumn;
    }
}
