package repository.snapshot;

import domain.game.Turn;
import java.util.List;

public record GameSnapshot(Long id, Turn currentTurn, GameStatus status, List<PieceSnapshot> pieces) {

    public GameSnapshot {
        pieces = List.copyOf(pieces);
    }
}
