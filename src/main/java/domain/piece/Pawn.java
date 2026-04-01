package domain.piece;

import domain.coordinate.Direction;
import domain.board.BoardBounds;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.Side;
import domain.rule.Rule;
import domain.rule.StepRule;
import domain.strategy.StepStrategy;
import domain.strategy.Strategy;

import java.util.List;
import java.util.Map;

public final class Pawn extends Piece {

    private final List<Direction> directions = List.of(
            forward(), Direction.LEFT, Direction.RIGHT);

    private final Strategy strategy = new StepStrategy(directions);
    private final Rule rule = new StepRule();

    public Pawn(Side side) {
        super(side);
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public Piece withSide(Side side) {
        return new Pawn(side);
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