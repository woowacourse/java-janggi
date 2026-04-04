package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.route.Paths;
import janggi.domain.strategy.CannonMoveStrategy;
import janggi.domain.strategy.ElephantMoveStrategy;
import janggi.domain.strategy.EmptyMoveStrategy;
import janggi.domain.strategy.HorseMoveStrategy;
import janggi.domain.strategy.MoveStrategy;
import janggi.domain.strategy.PalaceMoveStrategy;
import janggi.domain.strategy.SlideMoveStrategy;
import janggi.domain.strategy.StepMoveStrategy;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum PieceType {

    PALACE(side -> eightDirections(), new PalaceMoveStrategy()),
    GUARD(side -> eightDirections(), new PalaceMoveStrategy()),
    CHARIOT(side -> fourDirections(), new SlideMoveStrategy()),
    CANNON(side -> fourDirections(), new CannonMoveStrategy()),
    HORSE(side -> fourDirections(), new HorseMoveStrategy()),
    ELEPHANT(side -> fourDirections(), new ElephantMoveStrategy()),
    SOLDIER(Side::getSoldierDirections, new StepMoveStrategy()),
    EMPTY(side -> EnumSet.noneOf(Direction.class), new EmptyMoveStrategy()),
    ;

    private final Function<Side, EnumSet<Direction>> directionProvider;
    private final MoveStrategy moveStrategy;

    PieceType(Function<Side, EnumSet<Direction>> directionProvider, MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
        this.directionProvider = directionProvider;
    }

    private static EnumSet<Direction> eightDirections() {
        return EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W,
                Direction.NE, Direction.NW, Direction.SE, Direction.SW);
    }

    private static EnumSet<Direction> fourDirections() {
        return EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W);
    }

    public Paths calculatePaths(Position current, Side side) {
        return moveStrategy.findMovablePaths(current, directionProvider.apply(side));
    }

    public List<Position> determineDestinations(Paths paths, Map<Position, Piece> boardState, Piece movingPiece) {
        return moveStrategy.determineDestinations(paths, boardState, movingPiece);
    }
}
