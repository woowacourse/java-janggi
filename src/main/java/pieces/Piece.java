package pieces;

import java.util.List;
import java.util.Objects;
import movepolicy.rule.MoveTrace;
import participant.Score;
import position.Position;

public class Piece {

    private final Side side;
    private final PieceType type;

    public Piece(Side side, PieceType type) {
        this.side = side;
        this.type = type;
    }

    public final boolean isSameSide(Side side) {
        return this.side == side;
    }

    public final boolean isSameSide(Piece piece) {
        return isSameSide(piece.side);
    }

    public final void validate(Position departure, Position destination) {
        if (!type.getMovement().canReach(departure, destination, side)) {
            throw new IllegalArgumentException("행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    public final void validate(Position departure, Position destination, MoveTrace moveTrace) {
        if (!type.getMovement().canReach(departure, destination, side)) {
            throw new IllegalArgumentException("행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
        type.getMoveRule().validate(moveTrace);
    }

    public final List<Position> findPathPositions(Position departure, Position destination) {
        return type.getMovement().findPathPositions(departure, destination, side);
    }

    public final PieceType getType() {
        return type;
    }

    public final boolean isPo() {
        return type.isPo();
    }

    public final boolean isGung() {
        return type.isGung();
    }

    public Score getScore() {
        return type.getScore();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return side == piece.side && type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, type);
    }
}
