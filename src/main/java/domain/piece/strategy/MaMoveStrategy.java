package domain.piece.strategy;

import domain.position.Position;
import java.util.List;
import java.util.stream.IntStream;

public class MaMoveStrategy implements MoveStrategy {
    private static final int MA_MOVE_SPACE = 1;
    private static final int DESTINATION_INDEX = 1;
    private static final int[][] D_ROW = {
            {-1, -2}, // 1. 위 -> 오른쪽
            {0, -1}, // 2. 오른쪽 -> 위
            {0, 1}, // 3. 오른쪽 -> 아래
            {1, 2}, // 4. 아래 -> 오른쪽
            {1, 2}, // 5. 아래 -> 왼쪽
            {0, 1}, // 6. 왼쪽 -> 아래
            {0, -1}, // 7. 왼쪽 -> 위
            {-1, -2}  // 8. 위 -> 왼쪽
    };
    private static final int[][] D_COLUMN = {
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
        return IntStream.range(0, D_ROW.length)
                .filter(index -> isEqualToDestination(index, start, destination))
                .mapToObj(index -> consistMovablePath(index, start))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(MoveStrategyErrorMessage.NOT_EXIST_MOVABLE_PATH.getMessage())
                );
    }

    private List<Position> consistMovablePath(int index, Position start) {
        int[] dRowOfSpecificAction = D_ROW[index];
        int[] dColumnOfSpecificAction = D_COLUMN[index];
        return IntStream.range(0, MA_MOVE_SPACE)
                .mapToObj(i -> start.go(dRowOfSpecificAction[i], dColumnOfSpecificAction[i]))
                .toList();
    }

    private boolean isEqualToDestination(int index, Position start, Position destination) {
        int[] dRowOfSpecificAction = D_ROW[index];
        int[] dColumnOfSpecificAction = D_COLUMN[index];
        Position destinationCandidate = start.go(dRowOfSpecificAction[DESTINATION_INDEX],
                dColumnOfSpecificAction[DESTINATION_INDEX]);

        return destination.equals(destinationCandidate);
    }
}
