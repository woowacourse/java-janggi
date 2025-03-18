package janggi.domain.piece;

import janggi.domain.Position;
import java.util.List;

public class Cannon implements PieceBehavior {

    @Override
    public String toName() {
        return "포";
    }

    @Override
    public List<Position> generateMovePosition(Position position) {
        return List.of();
    }
}
