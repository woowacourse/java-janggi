package domain.piece;

import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Guard extends Piece {

    public Guard(PieceColor color) {
        super(PieceType.GUARD, color, DefaultMoveRule.getInstance());
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
