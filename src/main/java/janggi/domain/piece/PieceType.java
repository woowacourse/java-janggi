package janggi.domain.piece;

import janggi.domain.board.BoardState;
import janggi.domain.movestrategy.*;
import janggi.domain.movestrategy.route.Direction;
import janggi.domain.movestrategy.route.Route;
import janggi.domain.movestrategy.rule.*;
import janggi.domain.position.Position;

import java.util.List;

import static janggi.domain.movestrategy.route.Direction.*;

public enum PieceType {
    CHA(createChaStrategy(), 13),
    PO(createPoStrategy(), 7),
    MA(createMaStrategy(), 5),
    SANG(createSangStrategy(), 3),
    SA(createGungSaStrategy(), 3),
    HAN_GUNG(createGungSaStrategy(), 0),
    CHO_GUNG(createGungSaStrategy(), 0),
    HAN_JOL(createJolStrategy(SOUTH), 2),
    CHO_JOL(createJolStrategy(NORTH), 2);

    private final MoveStrategy moveStrategy;
    private final int score;

    PieceType(MoveStrategy moveStrategy, int score) {
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public boolean canMove(Position from, Position to, BoardState boardState) {
        return moveStrategy.canMove(from, to, boardState);
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public int getScore() {
        return score;
    }

    private static MoveStrategy createChaStrategy() {
        return new SlidingMoveStrategy(List.of(
                new StraightLineRule(),
                new EmptyPathRule()
        ));
    }

    private static MoveStrategy createPoStrategy() {
        return new SlidingMoveStrategy(List.of(
                new StraightLineRule(),
                new SingleJumpRule()
        ));
    }

    private static MoveStrategy createMaStrategy() {
        return new FixedStepMoveStrategy(List.of(
                new Route(List.of(SOUTH, SOUTH_EAST)),
                new Route(List.of(SOUTH, SOUTH_WEST)),
                new Route(List.of(NORTH, NORTH_EAST)),
                new Route(List.of(NORTH, NORTH_WEST)),
                new Route(List.of(EAST, NORTH_EAST)),
                new Route(List.of(EAST, SOUTH_EAST)),
                new Route(List.of(WEST, NORTH_WEST)),
                new Route(List.of(WEST, SOUTH_WEST))
        ));
    }

    private static MoveStrategy createSangStrategy() {
        return new FixedStepMoveStrategy(List.of(
                new Route(List.of(SOUTH, SOUTH_EAST, SOUTH_EAST)),
                new Route(List.of(SOUTH, SOUTH_WEST, SOUTH_WEST)),
                new Route(List.of(NORTH, NORTH_EAST, NORTH_EAST)),
                new Route(List.of(NORTH, NORTH_WEST, NORTH_WEST)),
                new Route(List.of(EAST, NORTH_EAST, NORTH_EAST)),
                new Route(List.of(EAST, SOUTH_EAST, SOUTH_EAST)),
                new Route(List.of(WEST, NORTH_WEST, NORTH_WEST)),
                new Route(List.of(WEST, SOUTH_WEST, SOUTH_WEST))
        ));
    }

    private static MoveStrategy createGungSaStrategy() {
        return new FixedStepMoveStrategy(List.of(
                new Route(List.of(NORTH)),
                new Route(List.of(SOUTH)),
                new Route(List.of(WEST)),
                new Route(List.of(EAST))
        ));
    }

    private static MoveStrategy createJolStrategy(Direction forward) {
        return new FixedStepMoveStrategy(List.of(
                new Route(List.of(forward)),
                new Route(List.of(WEST)),
                new Route(List.of(EAST))
        ));
    }
}
