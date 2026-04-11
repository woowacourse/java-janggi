package domain.movement;

import domain.board.BoardState;
import domain.board.Position;
import domain.movement.vo.Direction;
import domain.movement.vo.Path;
import domain.movement.vo.Paths;
import java.util.List;

public final class GuardMovement implements Movement {
    private static final List<Direction> MOVEMENT_RULES = List.of(
            Direction.UP,
            Direction.RIGHT,
            Direction.DOWN,
            Direction.LEFT
    );

    @Override
    public Paths findPotentialPaths(Position source) {
        List<Direction> validDirections = filterValidDirections(source);
        return parseDirectionsToPaths(source, validDirections);
    }

    private List<Direction> filterValidDirections(Position source) {
        return MOVEMENT_RULES.stream()
                .filter(direction -> isValidMove(source, direction))
                .toList();
    }

    private boolean isValidMove(Position source, Direction direction) {
        return source.canShift(direction.delta());
    }

    private Paths parseDirectionsToPaths(Position source, List<Direction> validDirections) {
        List<Path> paths = validDirections.stream()
                .map(direction -> createPath(source, direction))
                .toList();

        return new Paths(paths);
    }

    private Path createPath(Position source, Direction direction) {
        Position destination = source.shift(direction.delta());
        return new Path(List.of(destination));
    }

    @Override
    public boolean isAvailablePath(Path path, BoardState board) {
        return true;
    }
}