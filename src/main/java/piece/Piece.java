package piece;

import java.util.List;
import java.util.Objects;
import pieceProperty.PieceType;
import pieceProperty.Position;

public abstract class Piece {

    protected Position position;

    public Piece(final Position position) {
        this.position = position;
    }

    public Position getBoardPosition() {
        return position;
    }

    public boolean isSamePosition(Position position) {
        return position.equals(this.position);
    }

    public abstract boolean canMoveTo(Position position);

    public abstract List<Position> makeRoute(Position position);

    public abstract void updateChessPiecePositionBy(Position position);

    public abstract boolean isKing();

    public abstract PieceType getPieceType();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
