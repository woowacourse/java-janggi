package janggi.domain.piece;

import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class Chariot extends Piece {
    public Chariot(PieceColor color) {
        super(color, PieceType.CHARIOT, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        return rowDifference == 0 || columnDifference == 0;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return source.getBetweenPositions(destination);
    }
}
