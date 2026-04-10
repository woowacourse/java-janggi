package strategy.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.palace.PalaceRouter;

public class RookMoveStrategy extends MoveStrategy {

    private static final int MAX_STRAIGHT_DISTANCE = 9;

    @Override
    public List<MovePath> getPaths(Piece piece, Position from, PalaceRouter router) {
        List<MovePath> paths = new ArrayList<>();
        paths.addAll(createStraightPaths(Direction.NORTH));
        paths.addAll(createStraightPaths(Direction.SOUTH));
        paths.addAll(createStraightPaths(Direction.EAST));
        paths.addAll(createStraightPaths(Direction.WEST));
        paths.addAll(createPalaceDiagonalPaths(from, router));
        return List.copyOf(paths);
    }

    private List<MovePath> createStraightPaths(Direction direction) {
        List<MovePath> paths = new ArrayList<>();
        for (int distance = 1; distance <= MAX_STRAIGHT_DISTANCE; distance++) {
            paths.add(new MovePath(stepsInDirection(direction, distance)));
        }
        return List.copyOf(paths);
    }

    private List<Direction> stepsInDirection(Direction direction, int distance) {
        List<Direction> steps = new ArrayList<>();
        for (int i = 0; i < distance; i++) {
            steps.add(direction);
        }
        return steps;
    }

    private List<MovePath> createPalaceDiagonalPaths(Position from, PalaceRouter router) {
        if (!router.isInsidePalace(from)) {
            return List.of();
        }
        List<MovePath> result = new ArrayList<>();
        for (Position adjacent : router.getDiagonalAdjacents(from)) {
            result.addAll(pathsFromAdjacent(from, adjacent, router));
        }
        return List.copyOf(result);
    }

    private List<MovePath> pathsFromAdjacent(
            Position from, Position adjacent, PalaceRouter router) {
        Optional<Direction> optionalStep = Direction.of(from, adjacent);
        if (optionalStep.isEmpty()) {
            return List.of();
        }
        return pathsFromAdjacentWithStep(optionalStep.get(), adjacent, router);
    }

    private List<MovePath> pathsFromAdjacentWithStep(
            Direction step, Position adjacent, PalaceRouter router) {
        List<MovePath> paths = new ArrayList<>();
        paths.add(new MovePath(List.of(step)));
        paths.addAll(twoStepPalaceDiagonalIfValid(adjacent, step, router));
        return List.copyOf(paths);
    }

    private List<MovePath> twoStepPalaceDiagonalIfValid(
            Position adjacent, Direction step, PalaceRouter router) {
        Position oppositeCorner = adjacent.next(step);
        if (!oppositeCorner.isInsideBoard()) {
            return List.of();
        }
        if (!router.isInsidePalace(oppositeCorner)) {
            return List.of();
        }
        return List.of(new MovePath(List.of(step, step)));
    }
}
