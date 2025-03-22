package janggi.domain.piece;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class Soldier extends Piece {

    public Soldier(PieceColor color) {
        super(color, PieceType.SOLDIER, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(PiecePath path) {
        int rowDifference = path.rowDifference();
        int columnDifference = path.columnDifference();

        if ((color == PieceColor.RED) && (rowDifference == 1 && columnDifference == 0)) {
            return true;
        }
        if ((color == PieceColor.BLUE) && (rowDifference == -1 && columnDifference == 0)) {
            return true;
        }
        if ((rowDifference == 0 && columnDifference == -1) || (rowDifference == 0 && columnDifference == 1)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        return List.of();
    }

    @Override
    public boolean isNotEmptyPiece() {
        return true;
    }
}
