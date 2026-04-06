package janggi.dto;

import janggi.domain.game.Side;
import janggi.domain.piece.PieceType;

public record PiecePositionSnapshot(
        String side,
        String pieceType,
        String pieceNumber,
        int rowIndex,
        int columnIndex
) {
    public PiecePositionSnapshot(Side side, PieceType pieceType, String pieceNumber, int rowIndex, int columnIndex) {
        this(side.name(), pieceType.name(), pieceNumber, rowIndex, columnIndex);
    }
}
