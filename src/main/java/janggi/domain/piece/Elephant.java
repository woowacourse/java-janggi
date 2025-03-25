package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(PieceColor color) {
        super(color, PieceType.ELEPHANT, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(PiecePath path) {
        int rowDifference = path.rowDifference();
        int columnDifference = path.columnDifference();

        if (Math.abs(rowDifference) == 3 && Math.abs(columnDifference) == 2) {
            return true;
        }
        if (Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 3) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        int rowDifference = path.rowDifference();
        int columnDifference = path.columnDifference();

        Direction firstDirection = Direction.from(rowDifference / 3, columnDifference / 3);
        Direction secondDirection = Direction.from(
                rowDifference / Math.abs(rowDifference),
                columnDifference / Math.abs(columnDifference)
        );

        return path.tracePositionsByDirection(List.of(firstDirection, secondDirection));
    }

    @Override
    public boolean isNotEmptyPiece() {
        return true;
    }
}
