package domain.piece;

import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.List;

public class Guard extends Piece {

    public Guard(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position start) {
        return List.of();
    }
}
