package pieces;

import java.util.List;
import movepolicy.move.LinearRouteMovement;
import movepolicy.move.Movement;
import movepolicy.rule.MoveRule;
import movepolicy.rule.PoMoveRule;
import position.Position;

public class Po extends Piece {

    private static final Movement MOVEMENT = new LinearRouteMovement();
    private final MoveRule moveRule;

    public Po(Side side) {
        super(side);
        this.moveRule = new PoMoveRule();
    }

    @Override
    public void validateDestination(Position departure, Position destination) {
        if (!MOVEMENT.canReach(departure, destination, side)) {
            throw new IllegalArgumentException("포의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
        if (!departure.isGapBiggerThanOne(destination)) {
            throw new IllegalArgumentException("포는 한 칸만 이동할 수 없습니다.");
        }
    }

    @Override
    public List<Position> findPathPositions(Position departure, Position destination) {
        return MOVEMENT.findPathPositions(departure, destination, side);
    }

    @Override
    public MoveRule getMoveRule() {
        return moveRule;
    }

    @Override
    public PieceType type() {
        return PieceType.PO;
    }

    @Override
    public boolean isPo() {
        return true;
    }
}
