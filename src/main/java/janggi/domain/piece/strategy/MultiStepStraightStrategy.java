package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.ArrayList;
import java.util.List;

public class MultiStepStraightStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(from, to);

        validateStraightMove(directionInformation);

        if (directionInformation.isRowBiggerThanCol()) {
            return createRowPath(from, directionInformation.rowDifference());
        }
        return createColumnPath(from, directionInformation.colDifference());
    }

    private void validateStraightMove(DirectionInformation directionInformation) {
        int sum = directionInformation.addAllDifference();
        int rowDifference = directionInformation.rowDifference();
        int colDifference = directionInformation.colDifference();

        if (sum != rowDifference && sum != colDifference) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }

        if (rowDifference == 0 && colDifference == 0) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    private List<Position> createRowPath(Position from, int rowDifference) {
        List<Position> path = new ArrayList<>();

        int rowDirection = rowDifference / Math.abs(rowDifference);
        while (rowDifference != 0) {
            from = from.moveRow(rowDirection);
            path.add(from);
            rowDifference -= rowDirection;
        }
        return path;
    }

    private List<Position> createColumnPath(Position from, int colDifference) {
        List<Position> path = new ArrayList<>();

        int columnDirection = colDifference / Math.abs(colDifference);
        while (colDifference != 0) {
            from = from.moveCol(columnDirection);
            path.add(from);
            colDifference -= columnDirection;
        }
        return path;
    }
}
