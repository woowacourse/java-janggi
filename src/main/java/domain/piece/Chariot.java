package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(PieceColor color) {
        super(PieceType.CHARIOT, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        return movePath.isStraight();
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        return movePath.getBetweenPositions();
    }
}
