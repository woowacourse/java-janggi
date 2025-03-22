package janggi.domain.piece;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class General extends Piece {

    public General(PieceColor color) {
        super(color, PieceType.GENERAL, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(PiecePath path) {
        return false;
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        return List.of();
    }

    @Override
    public boolean isNotEmptyPiece() {
        return true;
    }
}
