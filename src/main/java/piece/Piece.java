package piece;

import java.util.Objects;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public abstract class Piece {

    protected Position position;

    public Piece(final Position position) {
        this.position = position;
    }

    public Position getBoardPosition() {
        return position;
    }

    public boolean isSamePosition(final Position position) {
        return position.equals(this.position);
    }

    public abstract void canMoveTo(final Position position);

    public abstract Positions makeRoute(final Position position);

    public void updateChessPiecePositionBy(final Position position) {
        this.position = position;
    }

    public abstract boolean isKing();

    public abstract boolean isPo();

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
