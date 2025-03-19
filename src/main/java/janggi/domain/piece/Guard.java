package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Guard implements PieceBehavior {

    private static final List<Vector> VECTORS = List.of(
            new Vector(1, 0), new Vector(0, -1), new Vector(0, 1), new Vector(-1, 0),
            new Vector(1, 1), new Vector(-1, -1), new Vector(1, -1), new Vector(-1, 1)
    );

    @Override
    public String toName() {
        return "사";
    }

    public Set<Position> generateMovePosition(Board board, Side side, Position position) {
        return VECTORS.stream()
                .map(vector -> vector.side(side))
                .map(position::calculate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(availablePosition -> board.canMoveToPosition(side, availablePosition))
                .collect(Collectors.toUnmodifiableSet());

    }
}
