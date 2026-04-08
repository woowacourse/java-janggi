package persistence.entity;

import domain.PieceProperty;
import domain.Position;
import java.util.Objects;

public record PieceState(
        PieceProperty pieceProperty,
        Position position
) {
    public PieceState {
        Objects.requireNonNull(pieceProperty);
        Objects.requireNonNull(position);
    }
}
