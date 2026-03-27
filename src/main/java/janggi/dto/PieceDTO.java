package janggi.dto;

import janggi.domain.game.Side;
import janggi.domain.piece.PieceType;
import janggi.util.PieceLabelMapper;

public record PieceDTO(String sideName, String label) {
    public PieceDTO(Side side, PieceType type, String pieceNumber) {
        this(side.name(), PieceLabelMapper.toLabel(side, type, pieceNumber));
    }
}
