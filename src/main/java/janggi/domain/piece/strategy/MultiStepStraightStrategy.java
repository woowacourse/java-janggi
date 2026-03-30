package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.ArrayList;
import java.util.List;

public class MultiStepStraightStrategy implements MoveStrategy {

    private static final String ONLY_STRAIGHT_MOVE_ALLOWED = "[ERROR] 해당 기물은 직선 이동만 가능합니다.";
    private static final String PIECE_MUST_MOVE = "[ERROR] 기물은 반드시 이동해야 합니다.";

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
            throw new IllegalArgumentException(ONLY_STRAIGHT_MOVE_ALLOWED);
        }

        if (rowDifference == 0 && columnDifference == 0) {
            throw new IllegalArgumentException(PIECE_MUST_MOVE);
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
