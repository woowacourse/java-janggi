package janggi.domain.piece.behavior;

import janggi.domain.Board;
import janggi.domain.move.Position;
import janggi.domain.Side;
import janggi.domain.move.Vector;
import janggi.domain.piece.PieceBehavior;
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
    public Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        return VECTORS.stream()
                .map(vector -> position.calculateNextPosition(vector.side(side)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(side, availablePosition))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String toName() {
        return "사";
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
