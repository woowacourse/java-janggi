package janggi.domain.piece;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class Horse extends Piece {

    public Horse(PieceColor color) {
        super(color, PieceType.HORSE, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(PiecePath path) {
        int rowDifference = path.rowDifference();
        int columnDifference = path.columnDifference();

        if(Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 1) {
            return true;
        }
        if(Math.abs(rowDifference) == 1 && Math.abs(columnDifference) == 2) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        Position route = path.getFractionalPosition(2);
        return List.of(route);
    }

    @Override
    public boolean isNotEmptyPiece() {
        return true;
    }
}
