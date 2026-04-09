package janggi.domain.piece;

import janggi.domain.piece.camp.CampType;

public class PlacedPiece {

    private Long placedPieceId;

    private Long gameId;

    private CampType campType;

    private PieceRule pieceRule;

    private int rowPosition;

    private int colPosition;

    public PlacedPiece(Long gameId, CampType campType, PieceRule pieceRule, int rowPosition, int colPosition) {
        this.gameId = gameId;
        this.campType = campType;
        this.pieceRule = pieceRule;
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
    }

    public Long getPlacedPieceId() {
        return placedPieceId;
    }

    public Long getGameId() {
        return gameId;
    }

    public CampType getCampType() {
        return campType;
    }

    public PieceRule getPieceRule() {
        return pieceRule;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColPosition() {
        return colPosition;
    }
}
