package janggi.dto;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

public record PieceDTO(Side side, PieceType type, String pieceNumber) {
    public static PieceDTO from(Piece piece) {
        return new PieceDTO(piece.getSide(), piece.getPieceType(), piece.getPieceNumber());
    }
}
