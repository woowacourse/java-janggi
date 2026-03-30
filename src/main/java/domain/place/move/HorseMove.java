package domain.place.move;

import domain.place.Place;
import domain.place.moveStrategy.Direction;
import domain.position.Position;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HorseMove implements Move{

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.TOP
    );

    private static final List<Direction> DIAGONAL_DIRECTIONS = List.of(
            Direction.LEFT_DOWN, Direction.LEFT_TOP, Direction.RIGHT_DOWN, Direction.RIGHT_TOP
    );

    @Override
    public List<Position> getPath(Position from) {
        return ORTHOGONAL_DIRECTIONS.stream()
                .flatMap(direction -> getValidPathPerDirection(from, direction).stream())
                .collect(Collectors.toList());
    }

    private List<Position> getValidPathPerDirection(Position from, Direction direction) {
        return from.moveIfInBounds(direction)
                .map(step1 -> findValidDiagonalSteps(step1, direction))
                .orElse(Collections.emptyList());
    }

    private List<Position> findValidDiagonalSteps(Position step1, Direction direction) {
        return DIAGONAL_DIRECTIONS.stream()
                .filter(diagonal -> isAlignedWith(direction, diagonal))
                .flatMap(diagonal -> step1.moveIfInBounds(direction).stream())
                .collect(Collectors.toList());
    }

    private boolean isAlignedWith(Direction straight, Direction diagonal) {
        return straight.getRow() == diagonal.getRow()
                || straight.getColumn() == diagonal.getColumn();
    }

    @Override
    public boolean canMove(Map<Position, Place> path, Position from, Position to) {
        if(path.containsKey(to) && )
        return ORTHOGONAL_DIRECTIONS.stream()
                .flatMap(direction -> from.moveIfInBounds(direction).stream())
                .anyMatch(to::equals);
    }




}
