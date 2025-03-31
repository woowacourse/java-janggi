package domain.piece;

import domain.direction.Directions;
import domain.piece.category.PieceType;
import domain.position.Position;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Directions directions;
    protected final Position position;

    public Piece(final int row, final int column, final Directions directions) {
        this.position = Position.of(row, column);
        this.directions = directions;
    }

    public Piece(final Position position, final Directions directions) {
        this.position = position;
        this.directions = directions;
    }

    public abstract Piece updatePosition(final Position position);

    public abstract String getName();

    public abstract boolean isEqualType(final PieceType type);

    public abstract int getScore();

    public abstract boolean isValidPosition(final Position position);

    public abstract boolean canMoveInPalace();

    public abstract PieceType getType();

    public List<Position> getPath(final Position targetPosition) {
        List<Position> path = directions.getPath(position, targetPosition);
        if (canMoveInPalace() && position.isInPalace() && targetPosition.isInPalace()) {
            List<Position> palacePath = directions.getPalacePath(position, targetPosition);
            path.addAll(palacePath);
        }
        return path;
    }

    public void validateInRangePosition(Position targetPosition) {
        if (!isValidPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 위치입니다.");
        }
    }

    public boolean isSamePosition(final Position position) {
        return this.position.equals(position);
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Piece piece = (Piece) object;
        return Objects.equals(directions, piece.directions) && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directions, position);
    }

    public Directions getDirections() {
        return directions;
    }

    public Position getPosition() {
        return position;
    }
}
