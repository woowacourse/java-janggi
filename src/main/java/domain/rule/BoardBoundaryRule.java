package domain.rule;

import domain.board.Board;
import domain.coordinate.Position;
import domain.piece.Piece;

public class BoardBoundaryRule implements MoveRule {

    @Override
    public boolean isValid(Board board, Position start, Position dest, Piece piece) {
        return board.isValidRange(dest);
    }
}
