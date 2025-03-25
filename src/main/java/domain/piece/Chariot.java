package domain.piece;

import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(PieceColor color) {
        super(PieceType.CHARIOT, color, DefaultMoveRule.getInstance());
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
