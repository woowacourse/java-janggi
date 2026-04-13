package persistence.entity;

import domain.GameStatus;
import java.util.List;
import java.util.Objects;

public record GameState(
        List<PieceState> pieceStates,
        GameStatus gameStatus
) {
    public GameState {
        Objects.requireNonNull(pieceStates);
        Objects.requireNonNull(gameStatus);
        pieceStates = List.copyOf(pieceStates);
    }
}
