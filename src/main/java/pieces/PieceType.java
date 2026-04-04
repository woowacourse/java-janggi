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
import movepolicy.move.GunsungDiagonalMovement;
import movepolicy.move.LinearRouteMovement;
import movepolicy.move.Movement;
import movepolicy.move.PoRouteMovement;
import movepolicy.move.Route;
import movepolicy.move.SingleStepGungsungMovement;
import movepolicy.rule.EmptyPathMoveRule;
import movepolicy.rule.MoveRule;
import movepolicy.rule.PoMoveRule;
import participant.Score;

public enum PieceType {

    CHA(
        new LinearRouteMovement(),
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(13)
    ),

    PO(
        new PoRouteMovement(),
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

    SA(
        new FixedRouteMovement(List.of(
            new Route(List.of(FORWARD)),
            new Route(List.of(BACK)),
            new Route(List.of(RIGHT)),
            new Route(List.of(LEFT))
        )),
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(3)
    ),

    JOL_BYEONG(
        new FixedRouteMovement(List.of(
            new Route(List.of(FORWARD)),
            new Route(List.of(RIGHT)),
            new Route(List.of(LEFT))
        )),
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(2)
    ),

    GUNG(
        new GunsungDiagonalMovement(
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
            new SingleStepGungsungMovement()
        ),
        EmptyPathMoveRule.withOtherSideTargetRule(),
        new Score(0)
    );

    private final Movement movement;
    private final MoveRule moveRule;
    private final Score score;

    PieceType(Movement movement, MoveRule moveRule, Score score) {
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
