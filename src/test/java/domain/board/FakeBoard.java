package domain.board;

import domain.piece.Piece;
import domain.piece.strategy.MovingCondition;
import domain.position.Position;

import java.util.HashMap;
import java.util.Map;

public class FakeBoard implements BoardState {
    private final Map<Position, Piece> state = new HashMap<>();

    @Override
    public boolean isBlocked(Position position) {
        return state.containsKey(position);
    }

    @Override
    public Piece findBy(Position position) {
        return state.get(position);
    }

    public FakeBoard put(Position position, Piece piece) {
        state.put(position, piece);
        return this;
    }
}
