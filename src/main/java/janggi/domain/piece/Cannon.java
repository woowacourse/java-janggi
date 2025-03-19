package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import java.util.Set;

public class Cannon implements PieceBehavior {

    @Override
    public String toName() {
        return "포";
    }

    @Override
    public Set<Position> generateMovePosition(Side side, Position position) {
        return Set.of();
    }

    @Override
    public Set<Position> generateMovePosition(Board board, Side side, Position position) {
        return Set.of();
    }
}
