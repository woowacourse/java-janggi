package domain.piece;

import domain.coordinate.Position;
import domain.rule.Rule;
import domain.strategy.Strategy;
import domain.Side;

import java.util.List;

public class EmptyPiece extends Piece {

    private static final EmptyPiece INSTANCE = new EmptyPiece();

    private EmptyPiece() {
        super(Side.NEUTRAL);
    }

    public static EmptyPiece getInstance() {
        return INSTANCE;
    }

    @Override
    public PieceType getType() {
        return PieceType.EMPTY;
    }

    @Override
    public Piece withSide(Side side) {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    protected Strategy getStrategy() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Rule getRule() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Position> getPossibleMoves(Position start, Pieces pieces) {
        return List.of();
    }
}
