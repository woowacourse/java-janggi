package domain;

import java.util.Map;

public record GameSnapshot(Map<Position, Piece> pieces) {

    public GameSnapshot {
        pieces = Map.copyOf(pieces);
    }

    public static GameSnapshot from(Map<Position, Piece> pieces) {
        return new GameSnapshot(pieces);
    }
}
