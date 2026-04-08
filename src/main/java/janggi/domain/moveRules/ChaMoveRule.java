package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChaMoveRule implements MoveRule {

    @Override
    public List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        int currentColumn = startPosition.getColumn();
        int currentRow = startPosition.getRow();
        List<Direction> straights = getStraightDirections();

        List<Position> result = new ArrayList<>();
        for (Direction direction : straights) {
            currentColumn += direction.getColumn();
            currentRow += direction.getRow();
            Position movePosition = new Position(currentColumn, currentRow);
            while (Position.isInsideBoundary(currentColumn, currentRow) && !state.containsKey(movePosition)) {
                result.add(movePosition);
                currentColumn += direction.getColumn();
                currentRow += direction.getRow();
            }
            if (state.containsKey(movePosition) && state.get(movePosition).isAlly(team)) {
                result.add(movePosition);
            }
        }
        return result;
    }

    private static List<Direction> getStraightDirections() {
        return Arrays.stream(Direction.values())
                .filter(Direction::isStraight)
                .toList();
    }
}
