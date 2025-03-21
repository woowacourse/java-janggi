package janggi.domain.piece;

import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class General extends Piece {

    public General(PieceColor color) {
        super(color, PieceType.GENERAL, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(Position source, Position destination) {
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return List.of();
    }
}
