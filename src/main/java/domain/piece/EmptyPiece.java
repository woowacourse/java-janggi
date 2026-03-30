package domain.piece;

import domain.board.Board;
import domain.coordinate.Position;
import domain.board.Side;

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
    public List<Position> getPossibleMoves(Board board, Position start) {
        return List.of();
    }
}
