package domain.piece;

import domain.board.BoardBounds;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.Side;

import java.util.List;
import java.util.Map;

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
    public List<Position> getPossibleMoves(Position start, BoardBounds bounds, Pieces pieces) {
        return List.of();
    }
}