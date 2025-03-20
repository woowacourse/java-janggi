package domain.piece;

import domain.direction.Directions;

import java.util.List;
import java.util.Objects;

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

    public abstract boolean isKing();

    public abstract String getName();

    public Directions getDirections() {
        return directions;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Piece piece = (Piece) object;
        return Objects.equals(directions, piece.directions) && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directions, position);
    }
}
