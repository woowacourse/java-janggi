package domain.piece;

import domain.board.BoardBounds;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.Side;
import domain.rule.CannonRule;
import domain.rule.Rule;
import domain.strategy.LinearStrategy;
import domain.strategy.Strategy;

import java.util.List;
import java.util.Map;

public final class Cannon extends Piece {

    private final Strategy strategy = new LinearStrategy();
    private final Rule rule = new CannonRule();

    public Cannon(Side side) {
        super(side);
    }

    @Override
    public Piece withSide(Side side) {
        return new Cannon(side);
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
