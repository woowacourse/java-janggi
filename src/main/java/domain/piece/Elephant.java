package domain.piece;

import domain.coordinate.Direction;
import domain.board.BoardBounds;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.Side;
import domain.rule.LeapRule;
import domain.rule.Rule;
import domain.strategy.SequenceStrategy;
import domain.strategy.Strategy;

import java.util.List;
import java.util.Map;

public final class Elephant extends Piece {

    private static final List<List<Direction>> SEQUENCES = List.of(
            List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT),
            List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT),
            List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
            List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
            List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT),
            List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
            List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT),
            List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));

    private final Strategy strategy = new SequenceStrategy(SEQUENCES);
    private final Rule rule = new LeapRule();

    public Elephant(Side side) {
        super(side);
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public Piece withSide(Side side) {
        return new Elephant(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<Path> getPaths(Position start, BoardBounds bounds) {
        return strategy.getPaths(start, bounds);
    }

    @Override
    public List<Position> getPossiblePositions(Map<Position, Piece> pathPieces, List<Path> paths) {
        return rule.getPossiblePositions(getSide(), pathPieces, paths);
    }
}