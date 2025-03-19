package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import java.util.Set;

public class General implements PieceBehavior {

    @Override
    public String toName() {
        return "궁";
    }

    @Override
    public Set<Position> generateMovePosition(Board board, Side side, Position position) {
        return Set.of();
    }
}
