package pieces;

import static movepolicy.move.Step.BACK;
import static movepolicy.move.Step.FORWARD;
import static movepolicy.move.Step.LEFT;
import static movepolicy.move.Step.LEFT_BACK;
import static movepolicy.move.Step.LEFT_FORWARD;
import static movepolicy.move.Step.RIGHT;
import static movepolicy.move.Step.RIGHT_BACK;
import static movepolicy.move.Step.RIGHT_FORWARD;

import java.util.List;
import movepolicy.move.FixedRouteMovement;
import movepolicy.move.LinearRouteMovement;
import movepolicy.move.Movement;
import movepolicy.move.PoRouteMovement;
import movepolicy.move.Route;
import movepolicy.rule.EmptyPathMoveRule;
import movepolicy.rule.MoveRule;
import movepolicy.rule.PoMoveRule;

public enum PieceType {

    CHA(
        new LinearRouteMovement(),
        EmptyPathMoveRule.withOtherSideTargetRule()
    ),

    PO(
        new PoRouteMovement(),
        PoMoveRule.withOtherSideTargetRule()
    ),

    MA(
        new FixedRouteMovement(List.of(
            new Route(List.of(FORWARD, RIGHT_FORWARD)),
            new Route(List.of(FORWARD, LEFT_FORWARD)),
            new Route(List.of(BACK, RIGHT_BACK)),
            new Route(List.of(BACK, LEFT_BACK)),
            new Route(List.of(LEFT, LEFT_FORWARD)),
            new Route(List.of(LEFT, LEFT_BACK)),
            new Route(List.of(RIGHT, RIGHT_FORWARD)),
            new Route(List.of(RIGHT, RIGHT_BACK))
        )),
        EmptyPathMoveRule.withOtherSideTargetRule()
    ),

    SANG(
        new FixedRouteMovement(List.of(
            new Route(List.of(FORWARD, RIGHT_FORWARD, RIGHT_FORWARD)),
            new Route(List.of(FORWARD, LEFT_FORWARD, LEFT_FORWARD)),
            new Route(List.of(BACK, RIGHT_BACK, RIGHT_BACK)),
            new Route(List.of(BACK, LEFT_BACK, LEFT_BACK)),
            new Route(List.of(LEFT, LEFT_FORWARD, LEFT_FORWARD)),
            new Route(List.of(LEFT, LEFT_BACK, LEFT_BACK)),
            new Route(List.of(RIGHT, RIGHT_FORWARD, RIGHT_FORWARD)),
            new Route(List.of(RIGHT, RIGHT_BACK, RIGHT_BACK))
        )),
        EmptyPathMoveRule.withOtherSideTargetRule()
    ),

    SA(
        new FixedRouteMovement(List.of(
            new Route(List.of(FORWARD)),
            new Route(List.of(BACK)),
            new Route(List.of(RIGHT)),
            new Route(List.of(LEFT))
        )),
        EmptyPathMoveRule.withOtherSideTargetRule()
    ),

    GUNG(
        new FixedRouteMovement(List.of(
            new Route(List.of(FORWARD)),
            new Route(List.of(BACK)),
            new Route(List.of(RIGHT)),
            new Route(List.of(LEFT))
        )),
        EmptyPathMoveRule.withOtherSideTargetRule()
    ),

    JOL_BYEONG(
        new FixedRouteMovement(List.of(
            new Route(List.of(FORWARD)),
            new Route(List.of(RIGHT)),
            new Route(List.of(LEFT))
        )),
        EmptyPathMoveRule.withOtherSideTargetRule()
    );

    private final Movement movement;
    private final MoveRule moveRule;

    PieceType(Movement movement, MoveRule moveRule) {
        this.movement = movement;
        this.moveRule = moveRule;
    }

    public Movement getMovement() {
        return movement;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }

    public boolean isPo() {
        return this == PO;
    }

    public Piece create(Side side) {
        return new Piece(side, this);
    }
}
