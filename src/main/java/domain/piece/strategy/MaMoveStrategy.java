package domain.piece.strategy;

import domain.PieceExceptionMessage;
import domain.position.Position;
import java.util.List;
import java.util.stream.IntStream;

public class MaMoveStrategy implements MoveStrategy {
    private static final int DESTINATION_INDEX = 1;
    private final int[][] dRow = {
            {-1, -2}, // 1. 위 -> 오른쪽
            {0, -1}, // 2. 오른쪽 -> 위
            {0, 1}, // 3. 오른쪽 -> 아래
            {1, 2}, // 4. 아래 -> 오른쪽
            {1, 2}, // 5. 아래 -> 왼쪽
            {0, 1}, // 6. 왼쪽 -> 아래
            {0, -1}, // 7. 왼쪽 -> 위
            {-1, -2}  // 8. 위 -> 왼쪽
    };
    private final int[][] dColumn = {
            {0, 1}, // 1. 위 -> 오른쪽
            {1, 2}, // 2. 오른쪽 -> 위
            {1, 2}, // 3. 오른쪽 -> 아래
            {0, 1}, // 4. 아래 -> 오른쪽
            {0, -1}, // 5. 아래 -> 왼쪽
            {-1, -2}, // 6. 왼쪽 -> 아래
            {-1, -2}, // 7. 왼쪽 -> 위
            {0, -1}  // 8. 위 -> 왼쪽
    };

    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        for (int direction = 0; direction < dColumn.length; direction++) {
            int[] rowSteps = dRow[direction];
            int[] columnSteps = dColumn[direction];

            Position destinationCandidate = start.go(rowSteps[DESTINATION_INDEX], columnSteps[DESTINATION_INDEX]);

            if (destination.equals(destinationCandidate)) {
                return getIntermediatePositions(start, rowSteps, columnSteps);
            }
        }
        throw new IllegalArgumentException(PieceExceptionMessage.INVALID_POSITION.getMessage());
    }

    private static List<Position> getIntermediatePositions(Position start, int[] rowSteps, int[] columnSteps) {
        return IntStream.range(0, DESTINATION_INDEX)
                .mapToObj(step -> start.go(rowSteps[step], columnSteps[step]))
                .toList();
    }
}
