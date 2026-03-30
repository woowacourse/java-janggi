package domain.rule;

import domain.board.Board;
import domain.coordinate.Position;
import domain.piece.Piece;

public class CannonCaptureRule implements MoveRule {

    @Override
    public boolean isValid(Board board, Position start, Position dest, Piece piece) {
        Piece target = board.getPiece(dest);

        if (target.isNeutral()) {
            return true;
        }

        return !(target.isCannon());
    }
}
