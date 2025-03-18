package janggi.domain.piece;

import janggi.domain.Position;
import java.util.List;

public class Soldier implements PieceBehavior {

    @Override
    public String toName() {
        return "병";
    }

    @Override
    public List<Position> generateMovePosition(Position position) {
        return List.of();
    }
}
