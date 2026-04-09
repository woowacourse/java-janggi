package entity;

import domain.board.Piece;
import domain.vo.Position;

public class BoardEntity {
    private final Long id;
    private final Long gameId;
    private final int positionRow;
    private final int positionCol;
    private final String team;
    private final String pieceType;

    public BoardEntity(Long gameId, int positionRow, int positionCol, String team, String pieceType) {
        this(null, gameId, positionRow, positionCol, team, pieceType);
    }

    public BoardEntity(Long id, Long gameId, int positionRow, int positionCol, String team, String pieceType) {
        this.id = id;
        this.gameId = gameId;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.team = team;
        this.pieceType = pieceType;
    }

    public static BoardEntity from(Long gameId, Piece piece, Position position) {
        return new BoardEntity(
                gameId,
                position.getRow(),
                position.getCol(),
                piece.getTeam().name(),
                piece.getType().name()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public String getTeam() {
        return team;
    }

    public String getPieceType() {
        return pieceType;
    }
}
