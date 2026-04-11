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

    GENERAL(side -> eightDirections(), new PalaceMoveStrategy(), 0.0),
    GUARD(side -> eightDirections(), new PalaceMoveStrategy(), 3.0),
    CHARIOT(side -> fourDirections(), new SlideMoveStrategy(), 13.0),
    CANNON(side -> fourDirections(), new CannonMoveStrategy(), 7.0),
    HORSE(side -> fourDirections(), new HorseMoveStrategy(), 5.0),
    ELEPHANT(side -> fourDirections(), new ElephantMoveStrategy(), 3.0),
    SOLDIER(Side::getSoldierDirections, new StepMoveStrategy(), 2.0),
    EMPTY(side -> EnumSet.noneOf(Direction.class), new EmptyMoveStrategy(), 0.0),
    ;

    private final Function<Side, EnumSet<Direction>> directionProvider;
    private final MoveStrategy moveStrategy;
    private final double score;

    PieceType(Function<Side, EnumSet<Direction>> directionProvider, MoveStrategy moveStrategy, double score) {
        this.moveStrategy = moveStrategy;
        this.directionProvider = directionProvider;
        this.score = score;
    }

    private static EnumSet<Direction> eightDirections() {
        return EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W,
                Direction.NE, Direction.NW, Direction.SE, Direction.SW);
    }

    private static EnumSet<Direction> fourDirections() {
        return EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W);
    }

    public Paths calculatePaths(Position current, Side side) {
        EnumSet<Direction> movableDirections = getMovableDirections(current, side);
        return moveStrategy.findMovablePaths(current, movableDirections);
    }

    private EnumSet<Direction> getMovableDirections(Position current, Side side) {
        EnumSet<Direction> directions = directionProvider.apply(side);

        if (this == SOLDIER && current.isOpponentPalace(side)) {
            addSoldierPalaceDiagonals(current, side, directions);
        }

        return directions;
    }

    private void addSoldierPalaceDiagonals(Position current, Side side, EnumSet<Direction> directions) {
        current.getValidPalaceDiagonals().stream()
                // 궁성 대각선들 중, 전진하는 방향만 추가
                .filter(diagonal -> diagonal.isForwardFor(side))
                .forEach(directions::add);
    }

    public List<Position> determineDestinations(Paths paths, Map<Position, Piece> boardState, Piece movingPiece) {
        return moveStrategy.determineDestinations(paths, boardState, movingPiece);
    }

    public double getScore() {
        return score;
    }
}
