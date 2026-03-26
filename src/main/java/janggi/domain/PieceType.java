package janggi.domain;

import janggi.domain.strategy.ElephantMoveStrategy;
import janggi.domain.strategy.HorseMoveStrategy;
import janggi.domain.strategy.MoveStrategy;
import janggi.domain.strategy.SlideMoveStrategy;
import janggi.domain.strategy.StepMoveStrategy;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public enum PieceType {

//    사이클 2 에서 궁성 구현 시
//    PALACE(EnumSet.allOf(Direction.class), new StepMoveStrategy()),
//    GUARD(EnumSet.allOf(Direction.class), new StepMoveStrategy()),
    PALACE(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new StepMoveStrategy()),
    GUARD(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new StepMoveStrategy()),
    CHARIOT(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new SlideMoveStrategy()),
    CANNON(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), null),
    HORSE(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new HorseMoveStrategy()),
    ELEPHANT(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new ElephantMoveStrategy()),
    CHO_SOLDIER(EnumSet.of(Direction.N, Direction.E, Direction.W), new StepMoveStrategy()),
    HAN_SOLDIER(EnumSet.of(Direction.S, Direction.E, Direction.W), new StepMoveStrategy()),
    ;

    private final EnumSet<Direction> directions;
    private final MoveStrategy moveStrategy;

    PieceType(EnumSet<Direction> directions, MoveStrategy moveStrategy) {
        this.directions = directions;
        this.moveStrategy = moveStrategy;
    }

    public Paths calculatePaths(Position current) {
        return moveStrategy.findMovablePaths(current, directions);
    }

    public List<Position> determineDestinations(Paths paths, Map<Position, PieceVO> boardState, PieceVO movingPieceVO) {
        return moveStrategy.determineDestinations(paths, boardState, movingPieceVO);
    }
}
