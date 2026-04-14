package domain.piece;

import domain.coordinate.Position;
import domain.Side;

import java.util.List;

public class EmptyPiece extends Piece {

    private static final EmptyPiece INSTANCE = new EmptyPiece();

    private EmptyPiece() {
        super(Side.NEUTRAL, null, null);
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
    public List<Position> getPossibleMoves(Position start, Pieces pieces) {
        return List.of();
    }
}
