package domain.piece;

import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.rule.Rule;
import domain.strategy.Strategy;
import domain.Side;

import java.util.List;
import java.util.Map;

public abstract class Piece {

    private final Side side;
    private final Strategy strategy;
    private final Rule rule;

    public Piece(Side side, Strategy strategy, Rule rule) {
        this.side = side;
        this.strategy = strategy;
        this.rule = rule;
    }

    public Side getSide() {
        return this.side;
    }

    public boolean isChu() {
        return side.isChu();
    }

    public boolean isHan() {
        return side.isHan();
    }

    public boolean isFriendly(Side side) {
        return this.side == side;
    }

    public List<Position> getPossibleMoves(Position start, Pieces pieces) {
        List<Path> paths = strategy.getPaths(start, pieces.getTopology());
        Map<Position, Piece> pathPieces = pieces.collectPieces(paths);
        return rule.getPossiblePositions(getSide(), pathPieces, paths);
    }

    public abstract PieceType getType();

    public abstract Piece withSide(Side side);

    public abstract boolean isEmpty();
}
