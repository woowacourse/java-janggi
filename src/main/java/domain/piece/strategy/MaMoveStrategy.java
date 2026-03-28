package domain.piece.strategy;

import common.ErrorMessage;
import domain.position.Position;
import java.util.List;
import java.util.stream.IntStream;

public class MaMoveStrategy implements MoveStrategy {
    private static final int MA_MOVE_SPACE = 1;
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
        for (int way = 0; way < dColumn.length; way++) { // 8가지 움직임중 하나.
            int[] dColumnOfSpecificAction = dColumn[way];
            int[] dRowOfSpecificAction = dRow[way];
            Position destinationCandidate = start.go(dRowOfSpecificAction[DESTINATION_INDEX],
                    dColumnOfSpecificAction[DESTINATION_INDEX]);
            if (destination.equals(destinationCandidate)) {
                return IntStream.range(0, MA_MOVE_SPACE)
                        .mapToObj(i -> start.go(dRowOfSpecificAction[i], dColumnOfSpecificAction[i]))
                        .toList();
            }
        }
        throw new IllegalArgumentException(ErrorMessage.INVALID_POS_INPUT.getMessage());
    }
}
