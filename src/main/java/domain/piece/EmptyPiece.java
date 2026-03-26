package domain.piece;

import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.List;

public class EmptyPiece extends Piece {

    public EmptyPiece() {
        super(Side.NEUTRAL);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position start) {
        return List.of();
    }
}
