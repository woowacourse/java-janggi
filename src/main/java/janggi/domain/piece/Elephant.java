package janggi.domain.piece;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.moveRule.DefaultMoveRule;
import java.util.ArrayList;
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
        List<Position> positions = new ArrayList<>();

        Position firstPosition = path.getFractionalPosition(3);
        Position secondPosition = path.getFactionalPositionToTarget(firstPosition, 2);

        positions.add(firstPosition);
        positions.add(secondPosition);
        return positions;
    }

    @Override
    public boolean isNotEmptyPiece() {
        return true;
    }
}
