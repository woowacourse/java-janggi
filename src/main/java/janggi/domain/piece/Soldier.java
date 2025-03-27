package janggi.domain.piece;

import janggi.domain.Direction;
import janggi.domain.Position;
import janggi.domain.Side;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Soldier extends Piece {

    private static final List<Direction> MOVEMENT_DIRECTIONS = List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT);

    public Soldier(Side side) {
        super(side);
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Map<Position, Piece> pieces, Position currentPosition) {
        return MOVEMENT_DIRECTIONS.stream()
                .map(Direction::getVector)
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
}
