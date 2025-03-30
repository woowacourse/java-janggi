package domain.piece.path;

import domain.position.Direction;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FixedSingleMovePathFinder implements PathFinder {
    private final List<Direction> directions;
    private final Map<Position, List<Direction>> palace_directions;

    public FixedSingleMovePathFinder(List<Direction> directions, Map<Position, List<Direction>> palaceDirections) {
        this.directions = directions;
        palace_directions = palaceDirections;
    }

    @Override
    public List<Position> findIntermediatePositions(Position from, Position to) {
        List<Direction> possibleDirections = new ArrayList<>(directions);
        if (palace_directions.containsKey(from)) {
            possibleDirections.addAll(palace_directions.get(from));
        }
        validateDestination(from, to, possibleDirections);
        return List.of();
    }

    private void validateDestination(Position from, Position to, List<Direction> possibleDirections) {
        boolean canNotMove = possibleDirections.stream()
                .filter(direction -> from.canMovePosition(direction.getDeltaRow(), direction.getDeltaColumn()))
                .map(direction -> from.movePosition(direction.getDeltaRow(), direction.getDeltaColumn()))
                .noneMatch(position -> position.equals(to));
        if (canNotMove) {
            throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
        }
    }
}
