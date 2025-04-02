package janggi.dao.entity;

import janggi.piece.PieceType;
import janggi.piece.Team;

public class PieceEntity {

    private Long id;
    private PieceType pieceType;
    private Team team;
    private int rowIndex;
    private int colIndex;
    private Long gameId;

    public PieceEntity(final Long id, final PieceType pieceType, final Team team, final int rowIndex,
                       final int colIndex, final Long gameId) {
        this.id = id;
        this.pieceType = pieceType;
        this.team = team;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.gameId = gameId;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public Long getGameId() {
        return gameId;
    }
}
