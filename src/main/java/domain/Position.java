package domain;

import java.util.Objects;

public class Position {

    private final Point point;
    private final Piece piece;

    public Position(final Point point, final Piece piece) {
        this.point = point;
        this.piece = piece;
    }

    public boolean isSame(final Point other) {
        return this.point.equals(other);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return Objects.equals(point, position.point);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(point);
    }
}
