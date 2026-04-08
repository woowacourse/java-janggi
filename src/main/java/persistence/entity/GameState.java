package persistence.entity;

import domain.GameStatus;
import domain.Team;
import java.util.List;
import java.util.Objects;

public record GameState(
        Team currentTurn,
        List<PieceState> pieceStates,
        GameStatus gameStatus
) {
    public GameState {
        Objects.requireNonNull(currentTurn);
        Objects.requireNonNull(pieceStates);
        Objects.requireNonNull(gameStatus);
        pieceStates = List.copyOf(pieceStates);
    }
}
