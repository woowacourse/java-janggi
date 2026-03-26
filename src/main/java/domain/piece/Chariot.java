package domain.piece;

import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.List;

public class Chariot extends Piece {

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position start) {
        return List.of();
    }
}
