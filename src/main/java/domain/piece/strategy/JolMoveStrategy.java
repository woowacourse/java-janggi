package domain.piece.strategy;

import common.ErrorMessage;
import domain.position.Position;
import java.util.List;

public class JolMoveStrategy implements MoveStrategy {
    private final int[] dRow = {1, 0, 0};
    private final int[] dColumn = {0, -1, 1};

    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        for (int i = 0; i < dColumn.length; i++) {
            Position changedPosition = start.go(dRow[i], dColumn[i]);
            if (changedPosition.equals(destination)) {
                return List.of();
            }
        }
        throw new IllegalArgumentException(ErrorMessage.INVALID_POS_INPUT.getMessage());
    }
}
