package domain.movement;

import domain.board.BoardState;
import domain.board.Position;
import domain.movement.palace.Palace;
import domain.movement.vo.Direction;
import domain.movement.vo.Path;
import domain.movement.vo.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ChariotMovement implements Movement {
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
        List<Path> straightPaths = validDirections.stream()
                .flatMap(direction -> createPaths(source, direction).stream())
                .toList();
        List<Path> diagonalPaths = createPalaceDiagonalPaths(source);
        List<Path> paths = Stream.concat(straightPaths.stream(), diagonalPaths.stream())
                .toList();

        return new Paths(paths);
    }

    private List<Path> createPaths(Position source, Direction direction) {
        List<Path> paths = new ArrayList<>();
        Position current = source;
        List<Position> route = new ArrayList<>();

        while (current.canShift(direction.delta())) {
            current = current.shift(direction.delta());
            route.add(current);
            paths.add(createPath(route));
        }

        return paths;
    }

    private Path createPath(List<Position> route) {
        return new Path(List.copyOf(route));
    }

    private List<Path> createPalaceDiagonalPaths(Position source) {
        if (Palace.isCenter(source)) {
            return createDiagonalPathsFromCenter(source);
        }
        if (Palace.isCorner(source)) {
            return createDiagonalPathsFromCorner(source);
        }
        return List.of();
    }

    private List<Path> createDiagonalPathsFromCenter(Position source) {
        return Palace.cornersOf(source).stream()
                .map(corner -> new Path(List.of(corner)))
                .toList();
    }

    private List<Path> createDiagonalPathsFromCorner(Position source) {
        Position center = Palace.centerOf(source);
        Position oppositeCorner = Palace.oppositeCornerOf(source);
        return List.of(
                new Path(List.of(center)),
                new Path(List.of(center, oppositeCorner))
        );
    }

    @Override
    public boolean isAvailablePath(Path path, BoardState board) {
        return path.positionsBeforeDestination().stream()
                .allMatch(board::isEmpty);
    }
}
