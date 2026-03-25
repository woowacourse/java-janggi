package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.ArrayList;
import java.util.List;

public class ChariotStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        int rowDiff = to.calculateRowDistance(from);
        int colDiff = to.calculateColumnDistance(from);

        validateStraightMove(rowDiff, colDiff);

        List<Position> path = new ArrayList<>();

        path.addAll(createRowPath(from, rowDiff));
        path.addAll(createColumnPath(from, colDiff));

        return path;
    }

    private void validateStraightMove(int rowDiff, int colDiff) {
        int sum = rowDiff + colDiff;

        if (sum != rowDiff && sum != colDiff) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    private List<Position> createRowPath(Position from, int rowDiff) {
        if (rowDiff == 0) {
            return List.of();
        }

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
        if (colDiff == 0) {
            return List.of();
        }

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
