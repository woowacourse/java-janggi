package pieces;

import java.util.List;
import java.util.Objects;
import movepolicy.move.Movement;
import movepolicy.rule.MoveRule;
import movepolicy.rule.MoveTrace;
import position.Position;

public class Piece {

    private final Side side;
    private final PieceType type;
    private final MoveRule moveRule;
    private final Movement movement;

    private Piece(Side side, PieceType type, MoveRule moveRule, Movement movement) {
        this.side = side;
        this.type = type;
        this.moveRule = moveRule;
        this.movement = movement;
    }

    public Piece(Side side, PieceType type) {
        this(side, type, type.getMoveRule(), type.getMovement());
    }

    public final boolean isSameSide(Side side) {
        return this.side == side;
    }

    public final boolean isSameSide(Piece piece) {
        return isSameSide(piece.side);
    }

    public final void validateDestination(Position departure, Position destination) {
        if (!movement.canReach(departure, destination, side)) {
            throw new IllegalArgumentException("행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    public final List<Position> findPathPositions(Position departure, Position destination) {
        return movement.findPathPositions(departure, destination, side);
    }

    public final void validate(MoveTrace moveTrace) {
        moveRule.validate(moveTrace);
    }

    public final boolean isPo() {
        return type.isPo();
    }

    public final boolean isGung() {
        return type == PieceType.GUNG;
    }

    public final PieceType getType() {
        return type;
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
