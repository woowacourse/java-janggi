package janggi.domain.piece;

import janggi.domain.board.Position;

import janggi.domain.moveRule.CannonMoveRule;
import java.util.List;

public class Cannon extends Piece {
    public Cannon(PieceColor color) {
        super(color, PieceType.CANNON, CannonMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        return rowDifference == 0 || columnDifference == 0;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return source.getBetweenPositions(destination);
    }

}
