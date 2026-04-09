package janggi.dto;

import janggi.domain.Position;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;

public record MoveResultDto(
        CampType campType,
        PieceRule pieceRule,
        Position source,
        Position destination,
        boolean captured
) {
}
