package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.ArrayList;
import java.util.List;

public class MultiStepStraightStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        int rowDiff = to.calculateRowDistance(from);
        int colDiff = to.calculateColumnDistance(from);

        validateStraightMove(rowDiff, colDiff);

        if (rowDiff != 0) {
            return createRowPath(from, rowDiff);
        }
        return createColumnPath(from, colDiff);
    }

    private void validateStraightMove(int rowDiff, int colDiff) {
        int sum = rowDiff + colDiff;

        if (sum != rowDiff && sum != colDiff) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    private List<Position> createRowPath(Position from, int rowDiff) {
        List<Position> path = new ArrayList<>();

        int rowDirection = rowDiff / Math.abs(rowDiff);
        while (rowDiff != 0) {
            from = from.moveRow(rowDirection);
            path.add(from);
            rowDiff -= rowDirection;
        }
        return path;
    }

    private List<Position> createColumnPath(Position from, int colDiff) {
        List<Position> path = new ArrayList<>();

        int columnDirection = colDiff / Math.abs(colDiff);
        while (colDiff != 0) {
            from = from.moveCol(columnDirection);
            path.add(from);
            colDiff -= columnDirection;
        }
        return path;
    }
}
