package domain.piece;

import domain.direction.Directions;
import java.util.List;

public abstract class Piece {

    private final Position position;
    private final Directions directions;

    public Piece(final int row, final int column, final Directions directions) {
        this.position = Position.of(row, column);
        this.directions = directions;
    }

    public Position getPosition() {
        return position;
    }

    public List<Position> getPath(final Position targetPosition) {
        return directions.getPath(position, targetPosition);
    }

    public abstract String getName();
}
