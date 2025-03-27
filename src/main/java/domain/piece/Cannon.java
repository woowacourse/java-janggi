package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.CannonMoveRule;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(PieceColor color) {
        super(PieceType.CANNON, color, CannonMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        return movePath.isStraight() || movePath.isDiagonalMoveBy(2);
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        return movePath.getBetweenPositions();
    }
}
