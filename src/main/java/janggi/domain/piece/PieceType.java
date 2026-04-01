package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.route.Paths;
import janggi.domain.strategy.CannonMoveStrategy;
import janggi.domain.strategy.ElephantMoveStrategy;
import janggi.domain.strategy.HorseMoveStrategy;
import janggi.domain.strategy.MoveStrategy;
import janggi.domain.strategy.SlideMoveStrategy;
import janggi.domain.strategy.StepMoveStrategy;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum PieceType {

//    TODO: 사이클 2 에서 궁성 구현 시
//    PALACE(EnumSet.allOf(Direction.class), new StepMoveStrategy()),
//    GUARD(EnumSet.allOf(Direction.class), new StepMoveStrategy()),
    PALACE(side -> EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new StepMoveStrategy()),
    GUARD(side -> EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new StepMoveStrategy()),
    CHARIOT(side -> EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new SlideMoveStrategy()),
    CANNON(side -> EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new CannonMoveStrategy()),
    HORSE(side -> EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new HorseMoveStrategy()),
    ELEPHANT(side -> EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), new ElephantMoveStrategy()),
    SOLDIER(Side::getSoldierDirections, new StepMoveStrategy()),
    ;

    private final Function<Side, EnumSet<Direction>> directionProvider;
    private final MoveStrategy moveStrategy;

    PieceType(Function<Side, EnumSet<Direction>> directionProvider, MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
        this.directionProvider = directionProvider;
    }

    public Paths calculatePaths(Position current, Side side) {
        return moveStrategy.findMovablePaths(current, directionProvider.apply(side));
    }

    public List<Position> determineDestinations(Paths paths, Map<Position, Piece> boardState, Piece movingPiece) {
        return moveStrategy.determineDestinations(paths, boardState, movingPiece);
    }
}
