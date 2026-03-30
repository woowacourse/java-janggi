package pieces;

import static movepolicy.move.OneStep.BACK;
import static movepolicy.move.OneStep.FORWARD;
import static movepolicy.move.OneStep.LEFT;
import static movepolicy.move.OneStep.LEFT_BACK;
import static movepolicy.move.OneStep.LEFT_FORWARD;
import static movepolicy.move.OneStep.RIGHT;
import static movepolicy.move.OneStep.RIGHT_BACK;
import static movepolicy.move.OneStep.RIGHT_FORWARD;

import java.util.List;
import movepolicy.move.FixedRouteMovement;
import movepolicy.move.Movement;
import movepolicy.move.Route;
import movepolicy.rule.BasicMoveRule;
import movepolicy.rule.MoveRule;
import position.Position;

public class Ma extends Piece {

    private static final Movement MOVEMENT = new FixedRouteMovement(List.of(
        new Route(List.of(FORWARD, RIGHT_FORWARD)),
        new Route(List.of(FORWARD, LEFT_FORWARD)),
        new Route(List.of(BACK, RIGHT_BACK)),
        new Route(List.of(BACK, LEFT_BACK)),
        new Route(List.of(LEFT, LEFT_FORWARD)),
        new Route(List.of(LEFT, LEFT_BACK)),
        new Route(List.of(RIGHT, RIGHT_FORWARD)),
        new Route(List.of(RIGHT, RIGHT_BACK))
    ));

    private final MoveRule moveRule;

    public Ma(Side side) {
        super(side);
        this.moveRule = new BasicMoveRule();
    }

    @Override
    public void validateDestination(Position departure, Position destination) {
        if (!MOVEMENT.canReach(departure, destination, side)) {
            throw new IllegalArgumentException("마의 행마법으로는 해당 위치로 이동할 수 없습니다.");
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
        return PieceType.MA;
    }

    @Override
    public boolean isPo() {
        return false;
    }
}
