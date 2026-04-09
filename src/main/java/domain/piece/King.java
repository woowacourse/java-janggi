package domain.piece;

import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.Side;
import domain.rule.Rule;
import domain.rule.StepRule;
import domain.strategy.PalaceStrategy;
import domain.strategy.StepStrategy;
import domain.strategy.Strategy;

import java.util.List;
import java.util.Map;

public final class King extends Piece {

    private final Strategy strategy = new PalaceStrategy(new StepStrategy());
    private final Rule rule = new StepRule();

    public King(Side side) {
        super(side);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public Piece withSide(Side side) {
        return new King(side);
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