package janggi.domain.piece;

import janggi.domain.game.Side;

public record PieceDTO(Side side, PieceType pieceType, String pieceNumber) {
    public static PieceDTO from(Piece piece) {
        return new PieceDTO(piece.getSide(), piece.getPieceType(), piece.getPieceNumber());
    }
}
