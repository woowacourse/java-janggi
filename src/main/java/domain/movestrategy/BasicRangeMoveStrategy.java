package domain.movestrategy;

import domain.Position;
import domain.move.Move;
import java.util.ArrayList;
import java.util.List;

public class BasicRangeMoveStrategy implements RangeMoveStrategy {

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        int rowDiff = startPosition.compareRow(targetPosition);
        int columnDiff = startPosition.compareColumn(targetPosition);

        validateSamePosition(rowDiff, columnDiff);
        return getPath(startPosition, rowDiff, columnDiff);
    }


    private List<Position> getPath(Position startPosition, int rowDiff, int columnDiff) {
        Move move;
        if (isVerticalMove(rowDiff)) {
            move = rowDiff < 0 ? Move.BACK : Move.FRONT;
            return determinePath(startPosition, move, Math.abs(rowDiff));
        }
        move = columnDiff < 0 ? Move.RIGHT : Move.LEFT;
        return determinePath(startPosition, move, Math.abs(columnDiff));
    }

    private void validateSamePosition(int rowDiff, int columnDiff) {
        if (rowDiff != 0 && columnDiff != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }
    }

    private boolean isVerticalMove(int rowDiff) {
        return rowDiff != 0;
    }

    private List<Position> determinePath(Position startPosition, Move moveDirection, int steps) {
        List<Position> path = new ArrayList<>();
        Position currentPosition = startPosition;

        for (int i = 0; i < steps - 1; i++) {
            if (currentPosition.canMovePosition(moveDirection)) {
                currentPosition = currentPosition.movePosition(moveDirection);
                path.add(currentPosition);
            }
        }
        return path;
    }

}
