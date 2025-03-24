package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.List;
import java.util.Map;
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
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        return VECTORS.stream()
                .map(vector -> currentPosition.calculateNextPosition(vector.side(side)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> canMoveToPosition(pieces, availablePosition))
                .collect(Collectors.toUnmodifiableSet());
    }

    private boolean canMoveToPosition(Map<Position, Piece> pieces, Position position) {
        if (!pieces.containsKey(position)) {
            return true;
        }
        Piece nextPiece = pieces.get(position);
        return !nextPiece.isSameSide(side);
    }

    @Override
    public boolean isGeneral() {
        return true;
    }
}
