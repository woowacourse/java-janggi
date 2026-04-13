package janggi.domain.piece;

import janggi.domain.piece.camp.CampType;

public class PlacedPiece {

    private Long placedPieceId;

    private Long gameRoomId;

    private CampType campType;

    private PieceRule pieceRule;

    private int rowPosition;

    private int colPosition;

    public PlacedPiece(Long gameRoomId, CampType campType, PieceRule pieceRule, int rowPosition, int colPosition) {
        this.gameRoomId = gameRoomId;
        this.campType = campType;
        this.pieceRule = pieceRule;
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
    }

    public PlacedPiece(Long placedPieceId, Long gameRoomId, CampType campType, PieceRule pieceRule, int rowPosition, int colPosition) {
        this.placedPieceId = placedPieceId;
        this.gameRoomId = gameRoomId;
        this.campType = campType;
        this.pieceRule = pieceRule;
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
    }

    public void moveTo(int rowPosition, int columnPosition) {
        this.rowPosition = rowPosition;
        this.colPosition = columnPosition;
    }

    public Long getPlacedPieceId() {
        return placedPieceId;
    }

    public Long getGameRoomId() {
        return gameRoomId;
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
