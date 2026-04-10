package domain.piece;

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
    public PieceType getType() {
        return PieceType.CANNON;
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
    public List<Position> getPossibleMoves(Position start, Pieces pieces) {
        List<Path> paths = strategy.getPaths(start, pieces.getTopology());
        Map<Position, Piece> pathPieces = pieces.collectPieces(paths);
        return rule.getPossiblePositions(getSide(), pathPieces, paths);
    }
}
