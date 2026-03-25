package janggi.fixture;

import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.Map;

public class FakeBoardDesignPolicy implements BoardDesignPolicy {
    @Override
    public Map<Position, Piece> initBoard() {
        return new HashMap<>(Map.of());
    }
}
