package piece;

import java.util.List;
import java.util.Objects;

public abstract class Piece {

    private final PieceProfile pieceProfile;
    protected Position position;

    public Piece(final PieceProfile pieceProfile, final Position position) {
        this.pieceProfile = pieceProfile;
        this.position = position;
    }

    public Position getBoardPosition() {
        return position;
    }

    public abstract boolean isMove(Position position);

    public abstract List<Position> makeRoute(Position position);

    public abstract void updateChessPiecePositionBy(Position position);

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece that = (Piece) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getBoardPosition(),
                that.getBoardPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBoardPosition());
    }

    public String getName() {
        return pieceProfile.getName();
    }

    public PieceProfile getPieceProfile() {
        return pieceProfile;
    }
}
