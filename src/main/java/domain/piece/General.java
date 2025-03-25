package domain.piece;

import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class General extends Piece {

    public General(PieceColor color) {
        super(PieceType.GENERAL, color, DefaultMoveRule.getInstance());
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
