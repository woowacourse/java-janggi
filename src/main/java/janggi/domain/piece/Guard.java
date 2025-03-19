package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import java.util.Set;

public class Guard implements PieceBehavior {

    @Override
    public String toName() {
        return "사";
    }

    @Override
    public Set<Position> generateMovePosition(Board board, Side side, Position position) {
        return Set.of();
    }
}
