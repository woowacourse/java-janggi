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

    //    TODO 사이클 2 에서 궁성 구현 시
    //    PALACE(EnumSet.allOf(Direction.class), new StepMoveStrategy()),
    //    GUARD(EnumSet.allOf(Direction.class), new StepMoveStrategy()),
    PALACE(side -> Direction.cardinalDirections(), new StepMoveStrategy()),
    GUARD(side -> Direction.cardinalDirections(), new StepMoveStrategy()),
    CHARIOT(side -> Direction.cardinalDirections(), new SlideMoveStrategy()),
    CANNON(side -> Direction.cardinalDirections(), new CannonMoveStrategy()),
    HORSE(side -> Direction.cardinalDirections(), new HorseMoveStrategy()),
    ELEPHANT(side -> Direction.cardinalDirections(), new ElephantMoveStrategy()),
    SOLDIER(side -> EnumSet.of(side.forwardDirection(), Direction.E, Direction.W), new StepMoveStrategy()),
    ;

    private final Function<Side, EnumSet<Direction>> directionProvider;
    private final MoveStrategy moveStrategy;

    PieceType(Function<Side, EnumSet<Direction>> directionProvider, MoveStrategy moveStrategy) {
        this.directionProvider = directionProvider;
        this.moveStrategy = moveStrategy;
    }

    public Paths calculatePaths(Position current, Side side) {
        return moveStrategy.findMovablePaths(current, directionProvider.apply(side));
    }

    public List<Position> determineDestinations(Paths paths, Map<Position, Piece> boardState, Piece movingPiece) {
        return moveStrategy.destinationsOf(, paths, , boardState);
    }
}
