package pieces;

import static movepolicy.move.OneStep.FORWARD;
import static movepolicy.move.OneStep.LEFT;
import static movepolicy.move.OneStep.RIGHT;

import java.util.List;
import movepolicy.move.FixedRouteMovement;
import movepolicy.move.Movement;
import movepolicy.move.Route;
import movepolicy.rule.BasicMoveRule;
import movepolicy.rule.MoveRule;
import position.Position;

public class JolByeong extends Piece {

    private static final Movement MOVEMENT = new FixedRouteMovement(List.of(
        new Route(List.of(FORWARD)),
        new Route(List.of(RIGHT)),
        new Route(List.of(LEFT))
    ));

    private final MoveRule moveRule;

    public JolByeong(Side side) {
        super(side);
        this.moveRule = new BasicMoveRule();
    }

    @Override
    public void validateDestination(Position departure, Position destination) {
        if (!MOVEMENT.canReach(departure, destination, side)) {
            throw new IllegalArgumentException("졸병의 행마법으로는 해당 위치로 이동할 수 없습니다.");
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
        return PieceType.JOL_BYEONG;
    }

    @Override
    public boolean isPo() {
        return false;
    }
}
