package domain.piece;

import domain.movestrategy.MoveStrategyType;

public record PieceStatus(
        PieceType pieceType,
        MoveStrategyType moveStrategyType
) {
}
