package janggi.position;

import janggi.piece.PieceType;
import janggi.piece.direction.Direction;
import janggi.piece.direction.Movement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Path {

    private final List<Position> positions;

    public Path(final List<Position> positions) {
        this.positions = positions;
    }

    public static Path from(final PieceType pieceType,
                            final Movement movement,
                            final Position startPosition,
                            final Position endPosition
    ) {
        final List<Position> path = new ArrayList<>();
        Position currentPosition = new Position(startPosition);
        for (final Direction direction : movement.getDirections()) {
            currentPosition = currentPosition.move(direction);
            path.add(currentPosition);
        }
        if (pieceType.isIterable()) {
            while (!currentPosition.equals(endPosition)) {
                currentPosition = currentPosition.move(movement.getFirstDirection());
                path.add(currentPosition);
            }
        }
        return new Path(path);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Path path)) {
            return false;
        }
        return Objects.equals(positions, path.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(positions);
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }
}
