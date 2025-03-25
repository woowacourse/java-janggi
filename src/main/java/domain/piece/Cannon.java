package domain.piece;

import domain.board.Position;
import domain.rule.CannonMoveRule;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(PieceColor color) {
        super(PieceType.CANNON, color, CannonMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        return rowDifference == NO_MOVE || columnDifference == NO_MOVE;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return source.getBetweenPositions(destination);
    }
}
