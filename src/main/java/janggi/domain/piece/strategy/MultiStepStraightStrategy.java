package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class MultiStepStraightStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        validateStraightMove(directionInformation);

        if (directionInformation.isRowBiggerThanColumn()) {
            return createRowPath(source, directionInformation.rowDifference());
        }
        return createColumnPath(source, directionInformation.columnDifference());
    }

    private void validateStraightMove(DirectionInformation directionInformation) {
        int sum = directionInformation.addAllDifference();
        int rowDifference = directionInformation.rowDifference();
        int columnDifference = directionInformation.columnDifference();

        if (sum != rowDifference && sum != columnDifference) {
            throw new IllegalArgumentException(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
        }

        if (rowDifference == 0 && columnDifference == 0) {
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

    private List<Position> createColumnPath(Position source, int columnDifference) {
        List<Position> path = new ArrayList<>();

        int columnDirection = columnDifference / Math.abs(columnDifference);
        while (columnDifference != 0) {
            source = source.moveColumn(columnDirection);
            path.add(source);
            columnDifference -= columnDirection;
        }
        return path;
    }
}
