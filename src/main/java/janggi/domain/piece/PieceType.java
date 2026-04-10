package janggi.domain.piece;

import janggi.domain.board.BoardState;
import janggi.domain.movestrategy.*;
import janggi.domain.movestrategy.route.Direction;
import janggi.domain.movestrategy.route.Route;
import janggi.domain.movestrategy.rule.*;
import janggi.domain.position.Position;
import janggi.exception.business.InvalidPieceTypeException;

import java.util.Arrays;
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
    HAN_JOL(createJolStrategy(SOUTH, SOUTH_WEST, SOUTH_EAST), 2),
    CHO_JOL(createJolStrategy(NORTH, NORTH_WEST, NORTH_EAST), 2);

    private final MoveStrategy moveStrategy;
    private final int score;

    PieceType(MoveStrategy moveStrategy, int score) {
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public static PieceType from(String name) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElseThrow(InvalidPieceTypeException::new);
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

    public boolean isKing() {
        return this == HAN_GUNG || this == CHO_GUNG;
    }

    private static MoveStrategy createChaStrategy() {
        MoveRule normalCha = new AndRule(new StraightLineRule(), new EmptyPathRule());
        MoveRule palaceCha = new PalaceChaDiagonalRule();
        MoveRule finalRule = new OrRule(normalCha, palaceCha);

        return new SlidingMoveStrategy(List.of(finalRule));
    }

    private static MoveStrategy createPoStrategy() {
        MoveRule normalPo = new AndRule(new StraightLineRule(), new SingleJumpRule());
        MoveRule palacePo = new PalacePoDiagonalRule();
        MoveRule finalRule = new OrRule(normalPo, palacePo);

        return new SlidingMoveStrategy(List.of(finalRule));
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
        MoveStrategy fixedStepMoveStrategy = new FixedStepMoveStrategy(List.of(
                new Route(List.of(NORTH)),
                new Route(List.of(SOUTH)),
                new Route(List.of(WEST)),
                new Route(List.of(EAST)),
                new Route(List.of(NORTH_WEST)),
                new Route(List.of(NORTH_EAST)),
                new Route(List.of(SOUTH_WEST)),
                new Route(List.of(SOUTH_EAST))
        ));
        return new PalaceBoundStrategy(fixedStepMoveStrategy);
    }

    private static MoveStrategy createJolStrategy(Direction forward, Direction diagLeft, Direction diagRight) {
        MoveStrategy normalStrategy = new FixedStepMoveStrategy(List.of(
                new Route(List.of(forward)),
                new Route(List.of(WEST)),
                new Route(List.of(EAST))
        ));
        MoveStrategy diagonalStrategy = new PalaceBoundStrategy(
                new FixedStepMoveStrategy(List.of(
                        new Route(List.of(diagLeft)),
                        new Route(List.of(diagRight))
                ))
        );
        return new OrStrategy(normalStrategy, diagonalStrategy);
    }
}
