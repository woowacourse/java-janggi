package domain.piece.strategy;

import domain.PieceExceptionMessage;
import domain.position.Position;
import java.util.List;

public class SingleStepMoveStrategy implements MoveStrategy {
    private final int[] dRow = {1, -1, 0, 0};
    private final int[] dColumn = {0, 0, -1, 1};

    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        for (int direction = 0; direction < dColumn.length; direction++) {
            Position changedPosition;
            try {
                changedPosition = start.go(dRow[direction], dColumn[direction]);
            } catch (IllegalArgumentException e) {
                continue;
            }
            if (changedPosition.equals(destination)) {
                return getIntermediatePositions();
            }
        }
        throw new IllegalArgumentException(PieceExceptionMessage.INVALID_POSITION.getMessage());
    }

    private static List<Position> getIntermediatePositions() {
        return List.of();
    }
}
