package janggi.dto;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.util.PieceLabelMapper;

public record PieceDto(String sideName, String label) {

    public PieceDto(Side side, PieceType type, String pieceNumber) {
        this(side.name(), PieceLabelMapper.toLabel(side, type, pieceNumber));
    }

    public static PieceDto from(Piece piece) {
        return new PieceDto(piece.side(), piece.type(), piece.pieceNumber());
    }
}
