package janggi.domain;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public enum PieceType {

//    사이클 2 에서 궁성 구현 시
//    PALACE(EnumSet.allOf(Direction.class), new StepMoveStrategy()),
//    GUARD(EnumSet.allOf(Direction.class), new StepMoveStrategy()),
    PALACE(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), null),
    GUARD(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), null),
    CHARIOT(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), null),
    CANNON(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), null),
    HORSE(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), null),
    ELEPHANT(EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), null),
    CHO_SOLDIER(EnumSet.of(Direction.N, Direction.E, Direction.W), null),
    HAN_SOLDIER(EnumSet.of(Direction.S, Direction.E, Direction.W), null);

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
