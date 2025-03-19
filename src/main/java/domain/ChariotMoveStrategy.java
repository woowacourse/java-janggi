package domain;

import static domain.Direction.DOWN;
import static domain.Direction.LEFT;
import static domain.Direction.RIGHT;
import static domain.Direction.UP;

import java.util.ArrayList;
import java.util.List;

public class ChariotMoveStrategy {

    private final List<Direction> directions = List.of(UP, DOWN, LEFT, RIGHT);

    public List<Path> getCoordinatePaths(ChessPosition startPosition) {
        final List<Path> paths = new ArrayList<>();
        for (Direction direction : directions) {
            final List<ChessPosition> chessPositions = new ArrayList<>();
            for (int distance = 1; distance <= 9; distance++) {
                final int nextRowDistance = direction.dr * distance;
                final int nextColumnDistance = direction.dc * distance;
                final int nextRow = startPosition.row() + nextRowDistance;
                final int nextColumn = startPosition.column() + nextColumnDistance;
                if (!ChessPosition.isValid(nextRow, nextColumn)) {
                    continue;
                }
                chessPositions.add(startPosition.move(nextRowDistance, nextColumnDistance));
            }
            if (!chessPositions.isEmpty()) {
                paths.add(new Path(chessPositions));
            }

        }
        return paths;
    }
}
