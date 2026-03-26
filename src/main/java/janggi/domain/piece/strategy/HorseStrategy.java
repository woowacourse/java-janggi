package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        int rowDifference = to.calculateRowDistance(from);
        int colDifference = to.calculateColumnDistance(from);

        int absRowDifference = Math.abs(rowDifference);
        int absColDifference = Math.abs(colDifference);

        validateHorseMovement(absRowDifference, absColDifference);

        int rowDirection = rowDifference / absRowDifference;
        int colDirection = colDifference / absColDifference;

        if (absRowDifference > absColDifference) {
            return createRowFirstPath(from, rowDirection, colDirection);
        }
        return createColFirstPath(from, rowDirection, colDirection);
    }

    private List<Position> createRowFirstPath(Position from, int rowDirection, int colDirection) {
        List<Position> path = new ArrayList<>();

        from = from.moveRow(rowDirection);
        path.add(from);

        from = from.moveDiagonal(rowDirection, colDirection);
        path.add(from);
        return path;
    }

    private List<Position> createColFirstPath(Position from, int rowDirection, int colDirection) {
        List<Position> path = new ArrayList<>();

        from = from.moveCol(colDirection);
        path.add(from);

        from = from.moveDiagonal(rowDirection, colDirection);
        path.add(from);
        return path;
    }

    private void validateHorseMovement(int rowDifference, int colDifference) {
        if ((rowDifference != 1 || colDifference != 2) && (rowDifference != 2 || colDifference != 1)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }
}
