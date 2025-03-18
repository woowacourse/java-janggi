package janggi.domain.piece;

import janggi.domain.Position;
import java.util.List;

public class General implements PieceBehavior {

    @Override
    public String toName() {
        return "궁";
    }

    @Override
    public List<Position> generateMovePosition(Position position) {
        return List.of();
    }
}
