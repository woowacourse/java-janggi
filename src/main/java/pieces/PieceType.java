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
import movepolicy.move.GungsungConstrainedMovement;
import movepolicy.move.GungsungDiagonalRouteMovement;
import movepolicy.move.GungsungExtendedMovement;
import movepolicy.move.GungsungForwardDiagonalMovement;
import movepolicy.move.GungsungStepMovement;
import movepolicy.move.LinearRouteMovement;
import movepolicy.move.Movement;
import movepolicy.move.PoRouteMovement;
import movepolicy.move.Route;
import movepolicy.rule.EmptyPathMoveRule;
import movepolicy.rule.MoveRule;
import movepolicy.rule.PoMoveRule;
import core.Score;

public enum PieceType {

    CHA(
        new GungsungExtendedMovement(
            new LinearRouteMovement(),
            new GungsungDiagonalRouteMovement()
        ),
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(13)
    ),

    PO(
        new GungsungExtendedMovement(
            new PoRouteMovement(),
            new GungsungDiagonalRouteMovement()
        ),
        PoMoveRule.withOtherSideTargetRule(),
        new Score(7)
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
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(5)
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
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(3)
    ),

    JOL_BYEONG(
        new GungsungExtendedMovement(
            new FixedRouteMovement(List.of(
                new Route(List.of(FORWARD)),
                new Route(List.of(RIGHT)),
                new Route(List.of(LEFT))
            )),
            new GungsungForwardDiagonalMovement()
        ),
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(2)
    ),

    SA(
        new GungsungConstrainedMovement(
            new FixedRouteMovement(List.of(
                new Route(List.of(FORWARD)),
                new Route(List.of(BACK)),
                new Route(List.of(RIGHT)),
                new Route(List.of(LEFT)),
                new Route(List.of(RIGHT_FORWARD)),
                new Route(List.of(LEFT_FORWARD)),
                new Route(List.of(RIGHT_BACK)),
                new Route(List.of(LEFT_BACK))
            )),
            new GungsungStepMovement()
        ),
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(3)
    ),

    GUNG(
        new GungsungConstrainedMovement(
            new FixedRouteMovement(List.of(
                new Route(List.of(FORWARD)),
                new Route(List.of(BACK)),
                new Route(List.of(RIGHT)),
                new Route(List.of(LEFT)),
                new Route(List.of(RIGHT_FORWARD)),
                new Route(List.of(LEFT_FORWARD)),
                new Route(List.of(RIGHT_BACK)),
                new Route(List.of(LEFT_BACK))
            )),
            new GungsungStepMovement()
        ),
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(0)
    ),
    ;

    private final Movement movement;
    private final MoveRule moveRule;
    private final Score score;

    PieceType(final Movement movement, final MoveRule moveRule, final Score score) {
        this.movement = movement;
        this.moveRule = moveRule;
        this.score = score;
    }

    public Movement getMovement() {
        return movement;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }

    public Score getScore() {
        return score;
    }

    public boolean isPo() {
        return this == PO;
    }

    public boolean isGung() {
        return this == GUNG;
    }

    public Piece create(Side side) {
        return new Piece(side, this);
    }
}
