package janggi.piece;

import janggi.piece.direction.Direction;
import janggi.piece.direction.Movement;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class NonIterablePiece extends Piece {

    public NonIterablePiece(final Team team, final Position currentPosition) {
        super(team, currentPosition);
    }

    @Override
    protected Path makePath(final Movement movement, final Position startPosition,
                            final Position arrivalPosition) {
        final List<Position> path = new ArrayList<>();
        Position currentPosition = new Position(startPosition);
        for (final Direction direction : movement.getDirections()) {
            currentPosition = currentPosition.move(direction);
            path.add(currentPosition);
        }
        return new Path(path);
    }
}
