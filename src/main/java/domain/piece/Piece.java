package domain.piece;

import domain.direction.Directions;

import java.util.List;

public abstract class Piece {

    protected final Directions directions;
    private final Position position;

    public Piece(final int row, final int column, final Directions directions) {
        this.position = Position.of(row, column);
        this.directions = directions;
    }

    public Piece(final Position position, final Directions directions) {
        this.position = position;
        this.directions = directions;
    }

    public List<Position> getPath(final Position targetPosition) {
        return directions.getPath(position, targetPosition);
    }

    public boolean isSamePosition(Position position) {
        return this.position.equals(position);
    }

    public abstract Piece updatePosition(final Position position);

    public abstract String getName();

    public Directions getDirections() {
        return directions;
    }

    public Position getPosition() {
        return position;
    }
}
