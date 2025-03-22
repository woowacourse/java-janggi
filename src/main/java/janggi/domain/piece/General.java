package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class General extends Piece {

    private static final List<Vector> VECTORS = List.of(
            new Vector(1, 0), new Vector(0, -1), new Vector(0, 1), new Vector(-1, 0),
            new Vector(1, 1), new Vector(-1, -1), new Vector(1, -1), new Vector(-1, 1)
    );

    public General(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Board board, Position position) {
        return VECTORS.stream()
                .map(vector -> position.calculateNextPosition(vector.side(side)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(side, availablePosition))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean isGeneral() {
        return true;
    }
}
