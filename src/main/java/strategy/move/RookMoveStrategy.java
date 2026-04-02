package strategy.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
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
        addPalaceDiagonalPathsIfPossible(from, router, paths);
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

    private void addPalaceDiagonalPathsIfPossible(Position from, PalaceRouter router, List<MovePath> paths) {
        if (!router.isInsidePalace(from)) {
            return;
        }
        for (Position adjacent : router.getDiagonalAdjacents(from)) {
            addDiagonalPathsFromAdjacent(from, adjacent, router, paths);
        }
    }

    private void addDiagonalPathsFromAdjacent(
            Position from, Position adjacent, PalaceRouter router, List<MovePath> paths) {
        Optional<Direction> optionalStep = Direction.of(from, adjacent);
        if (optionalStep.isEmpty()) {
            return;
        }
        Direction step = optionalStep.get();
        paths.add(new MovePath(List.of(step)));
        addTwoStepPalaceDiagonalIfValid(adjacent, step, router, paths);
    }

    private void addTwoStepPalaceDiagonalIfValid(
            Position adjacent, Direction step, PalaceRouter router, List<MovePath> paths) {
        Position oppositeCorner = adjacent.next(step);
        if (!oppositeCorner.isInsideBoard()) {
            return;
        }
        if (!router.isInsidePalace(oppositeCorner)) {
            return;
        }
        paths.add(new MovePath(List.of(step, step)));
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Piece pieceAtDestination, TeamColor myTeam) {
        if (!blockingPieces.isEmpty()) {
            return false;
        }
        return pieceAtDestination == null || !pieceAtDestination.isOnTeam(myTeam);
    }
}
