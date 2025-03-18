package janggi.domain.piece;

import janggi.domain.Position;
import java.util.List;
import java.util.Optional;

public class Soldier implements PieceBehavior {

    private static final List<Vector> VECTORS = List.of(new Vector(1, 0), new Vector(0, -1), new Vector(0, 1));

    @Override
    public String toName() {
        return "병";
    }

    @Override
    public List<Position> generateMovePosition(Side side, Position position) {
        return VECTORS.stream()
                .map(vector -> vector.side(side))
                .map(position::calculate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
