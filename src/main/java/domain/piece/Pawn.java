package domain.piece;

import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.Side;
import domain.rule.Rule;
import domain.rule.StepRule;
import domain.strategy.ForwardStrategy;
import domain.strategy.StepStrategy;
import domain.strategy.Strategy;

import java.util.List;
import java.util.Map;

public final class Pawn extends Piece {

    private final Strategy strategy;
    private final Rule rule = new StepRule();

    public Pawn(Side side) {
        super(side);
        this.strategy = new ForwardStrategy(new StepStrategy(), forward());
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
    public List<Position> getPossibleMoves(Position start, Pieces pieces) {
        List<Path> paths = strategy.getPaths(start, pieces.getTopology());
        Map<Position, Piece> pathPieces = pieces.collectPieces(paths);
        return rule.getPossiblePositions(getSide(), pathPieces, paths);
    }
}