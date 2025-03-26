package domain.piece;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Directions directions;
    private final Position position;

    public Piece(final Position position, final Directions directions) {
        this.position = position;
        this.directions = directions;
    }

    public abstract PieceCategory getCategory();

    public abstract Piece move(final Position position, final MoveInfos moveInfos);

    public boolean isKing() {
        return false;
    }

    public List<Position> getPaths(final Position targetPosition) {
        return directions.getPaths(position, targetPosition);
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
