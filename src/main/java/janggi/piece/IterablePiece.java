package janggi.piece;

import janggi.piece.direction.Movement;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class IterablePiece extends Piece {

    public IterablePiece(final Team team, final Position currentPosition) {
        super(team, currentPosition);
    }

    @Override
    protected Path makePath(final Movement movement, final Position startPosition,
                            final Position arrivalPosition) {
        final List<Position> path = new ArrayList<>();
        Position currentPosition = new Position(startPosition);
        while (!currentPosition.equals(arrivalPosition)) {
            currentPosition = currentPosition.move(movement.getFirstDirection());
            path.add(currentPosition);
        }
        return new Path(path);
    }
}
