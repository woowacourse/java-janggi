package domain.piece.strategy;

import common.ErrorMessage;
import domain.position.Position;
import java.util.List;
import java.util.stream.IntStream;

public class ByeongMoveStrategy implements MoveStrategy {
    private final int[] dRow = {-1, 0, 0};
    private final int[] dColumn = {0, -1, 1};

    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        return IntStream.range(0, dRow.length)
                .filter(index -> isEqualToDestination(start, destination, index))
                .mapToObj(index -> List.<Position>of())
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(ErrorMessage.INVALID_POS_INPUT.getMessage())
                );
    }

    private boolean isEqualToDestination(Position start, Position destination, int i) {
        Position changedPosition = start.go(dRow[i], dColumn[i]);
        return changedPosition.equals(destination);
    }
}
