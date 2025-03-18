package janggi.domain.piece;

import janggi.domain.Position;
import java.util.List;

public class Horse implements PieceBehavior {

    @Override
    public String toName() {
        return "마";
    }

    @Override
    public List<Position> generateMovePosition(Side side, Position position) {
        return List.of();
    }
}
