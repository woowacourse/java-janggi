package domain.piece;

import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Soldier extends Piece {

    public static final int FORWARD_MOVE = 1;
    public static final int BACKWARD_MOVE = -1;

    public Soldier(PieceColor color) {
        super(PieceType.SOLDIER, color, DefaultMoveRule.getInstance());
    }

    @Override
    public boolean isValidMovement(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if ((color == PieceColor.RED) && (rowDifference == FORWARD_MOVE && columnDifference == NO_MOVE)) {
            return true;
        }
        if ((color == PieceColor.BLUE) && (rowDifference == BACKWARD_MOVE && columnDifference == NO_MOVE)) {
            return true;
        }
        if ((rowDifference == NO_MOVE && columnDifference == BACKWARD_MOVE) || (rowDifference == NO_MOVE
                && columnDifference == FORWARD_MOVE)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return List.of();
    }
}
