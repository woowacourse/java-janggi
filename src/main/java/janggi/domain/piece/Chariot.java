package janggi.domain.piece;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class Chariot extends Piece {
    public Chariot(PieceColor color) {
        super(color, PieceType.CHARIOT, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(PiecePath path) {
        return path.isStraight();
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        return path.getBetweenPositions();
    }

    @Override
    public boolean isNotEmptyPiece() {
        return true;
    }
}
