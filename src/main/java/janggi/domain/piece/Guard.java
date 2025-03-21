package janggi.domain.piece;

import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class Guard extends Piece {

    public Guard(PieceColor color) {
        super(color, PieceType.GUARD, DefaultMoveRule.getRule());
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
