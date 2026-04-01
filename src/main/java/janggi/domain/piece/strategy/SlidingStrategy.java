package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class SlidingStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        validateStraightMove(directionInformation);

        if (directionInformation.isRowBiggerThanCol()) {
            return createRowPath(source, directionInformation.rowDifference());
        }
        return createColumnPath(source, directionInformation.colDifference());
    }

    private void validateStraightMove(DirectionInformation directionInformation) {
        int sum = directionInformation.addAllDifference();
        int rowDifference = directionInformation.rowDifference();
        int colDifference = directionInformation.colDifference();

        if (sum != rowDifference && sum != colDifference) {
            throw new IllegalArgumentException(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
        }

        if (rowDifference == 0 && colDifference == 0) {
            throw new IllegalArgumentException(ExceptionMessage.PIECE_MUST_MOVE.getMessage());
        }
    }

    private List<Position> createRowPath(Position source, int rowDifference) {
        List<Position> path = new ArrayList<>();

        int rowDirection = rowDifference / Math.abs(rowDifference);
        while (rowDifference != 0) {
            source = source.moveRow(rowDirection);
            path.add(source);
            rowDifference -= rowDirection;
        }
        return path;
    }

    private List<Position> createColumnPath(Position source, int colDifference) {
        List<Position> path = new ArrayList<>();

        int columnDirection = colDifference / Math.abs(colDifference);
        while (colDifference != 0) {
            source = source.moveCol(columnDirection);
            path.add(source);
            colDifference -= columnDirection;
        }
        return path;
    }
}
