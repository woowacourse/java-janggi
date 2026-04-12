package domain.movement;

import domain.board.BoardState;
import domain.board.Position;
import domain.movement.vo.Delta;
import domain.movement.vo.Direction;
import domain.movement.vo.Directions;
import domain.movement.vo.Path;
import domain.movement.vo.Paths;
import java.util.List;

public final class ElephantMovement implements Movement {
    private static final List<Directions> MOVEMENT_RULES = List.of(
            new Directions(List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT)),
            new Directions(List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT)),
            new Directions(List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT)),
            new Directions(List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)),
            new Directions(List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)),
            new Directions(List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT)),
            new Directions(List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT)),
            new Directions(List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT))
    );

    @Override
    public Paths findPotentialPaths(Position source) {
        List<Directions> validDirections = filterValidDirections(source);

        return parseDirectionsToPaths(source, validDirections);
    }

    private List<Directions> filterValidDirections(Position source) {
        return MOVEMENT_RULES.stream()
                .filter(direction -> isValidMove(source, direction))
                .toList();
    }

    private boolean isValidMove(Position source, Directions movement) {
        Delta finalDestinationDelta = movement.calculateFinalDestinationDelta();
        return source.canShift(finalDestinationDelta);
    }

    private Paths parseDirectionsToPaths(Position source, List<Directions> validDirections) {
        List<Path> paths = validDirections.stream()
                .map(direction -> createPath(source, direction))
                .toList();

        return new Paths(paths);
    }

    private Path createPath(Position source, Directions direction) {
        return new Path(direction.apply(source));
    }

    @Override
    public boolean isAvailablePath(Path path, BoardState board) {
        return path.positionsBeforeDestination().stream()
                .allMatch(board::isEmpty);
    }
}
