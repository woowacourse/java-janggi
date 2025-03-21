package janggi.domain.piece;

import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(PieceColor color) {
        super(color, PieceType.ELEPHANT, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if (Math.abs(rowDifference) == 3 && Math.abs(columnDifference) == 2) {
            return true;
        }
        if (Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 3) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        List<Position> positions = new ArrayList<>();

        Position firstPosition = source.getPositionByFraction(destination, 3);
        Position secondPosition = firstPosition.getPositionByFraction(destination, 2);

        positions.add(firstPosition);
        positions.add(secondPosition);
        return positions;
    }
}
