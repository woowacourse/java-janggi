package janggi.domain.piece;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Destinations;
import janggi.domain.board.Direction;
import janggi.domain.board.PalacePosition;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.strategy.CannonMoveStrategy;
import janggi.domain.strategy.ElephantMoveStrategy;
import janggi.domain.strategy.HorseMoveStrategy;
import janggi.domain.strategy.MoveStrategy;
import janggi.domain.strategy.PalaceMoveStrategy;
import janggi.domain.strategy.SlideMoveStrategy;
import janggi.domain.strategy.StepMoveStrategy;
import java.util.EnumSet;
import java.util.function.Function;

public enum PieceType {

    PALACE(side -> Direction.cardinalDirections(), new PalaceMoveStrategy(), 0),
    GUARD(side -> Direction.cardinalDirections(), new PalaceMoveStrategy(), 3),
    CHARIOT(side -> Direction.cardinalDirections(), new SlideMoveStrategy(), 13),
    CANNON(side -> Direction.cardinalDirections(), new CannonMoveStrategy(), 7),
    HORSE(side -> Direction.cardinalDirections(), new HorseMoveStrategy(), 5),
    ELEPHANT(side -> Direction.cardinalDirections(), new ElephantMoveStrategy(), 3),
    SOLDIER(side -> EnumSet.of(side.forwardDirection(), Direction.E, Direction.W), new StepMoveStrategy(), 2),
    ;

    private final Function<Side, EnumSet<Direction>> directionProvider;
    private final MoveStrategy moveStrategy;
    private final int score;

    PieceType(Function<Side, EnumSet<Direction>> directionProvider, MoveStrategy moveStrategy, int score) {
        this.directionProvider = directionProvider;
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Destinations determineDestinations(Position currentPosition, Side side, BoardInfo boardInfo) {
        EnumSet<Direction> normalDirections = directionProvider.apply(side);
        EnumSet<Direction> palaceDirections = PalacePosition.palaceDirections(currentPosition);

        Destinations normalDestinations = moveStrategy.findDestinations(currentPosition, normalDirections, boardInfo);
        Destinations palaceDestinations = moveStrategy.findDestinations(currentPosition, palaceDirections, boardInfo);

        palaceDestinations = palaceDestinations.retainDestination(PalacePosition.palacePositions());
        return normalDestinations.addDestinations(palaceDestinations);
    }
}
