package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import java.util.HashSet;
import java.util.Set;

public class Elephant implements PieceBehavior {

    @Override
    public String toName() {
        return "상";
    }

    @Override
    public Set<Position> generateMovePosition(Side side, Position position) {
        return new HashSet<>();
    }

    @Override
    public Set<Position> generateMovePosition(Board board, Side side, Position position) {
        return Set.of();
    }
}
