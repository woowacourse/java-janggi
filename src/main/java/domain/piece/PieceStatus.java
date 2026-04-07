package domain.piece;

import domain.movestrategy.MoveStrategy;

public record PieceStatus(
        PieceType pieceType,
        MoveStrategy moveStrategy
) {
}
