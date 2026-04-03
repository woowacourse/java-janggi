package janggi.domain.board.initializer;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public final class SnapshotBoardInitializer implements BoardInitializer {

    private final Map<Position, Piece> snapshot;

    public SnapshotBoardInitializer(Map<Position, Piece> snapshot) {
        this.snapshot = Map.copyOf(snapshot);
    }

    @Override
    public Map<Position, Piece> initialize() {
        return new HashMap<>(snapshot);
    }
}
