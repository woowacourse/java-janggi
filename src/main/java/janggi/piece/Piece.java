package janggi.piece;

import janggi.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {

    private final PieceProfile pieceProfile;
    protected Position position;

    protected Piece(final PieceProfile pieceProfile, final Position position) {
        this.pieceProfile = pieceProfile;
        this.position = position;
    }

    public abstract void updatePiecePositionBy(Position position);

    public abstract void checkObstacle(final Position futurePosition, Map<Position, Piece> janggiBoard);

    public abstract List<Position> makeRoute(Position position);

    public abstract boolean isMove(Position position);

    public boolean isChoNation() {
        return pieceProfile.isCho();
    }

    public boolean isHanNation() {
        return pieceProfile.isHan();
    }

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

    public Position getBoardPosition() {
        return position;
    }

    public String getName() {
        return pieceProfile.getName();
    }
}
