package strategy.move;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.palace.PalaceRouter;

public class RookMoveStrategy extends MoveStrategy {

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
        for (int distance = 1; distance <= 9; distance++) {
            List<Direction> steps = new ArrayList<>();
            for (int i = 0; i < distance; i++) {
                steps.add(direction);
            }
            paths.add(new MovePath(steps));
        }
        return List.copyOf(paths);
    }

    private void addPalaceDiagonalPathsIfPossible(Position from, PalaceRouter router, List<MovePath> paths) {
        if (!router.isInsidePalace(from)) {
            return;
        }

        addSingleStepPalaceDiagonals(from, router, paths);
        addTwoStepPalaceDiagonalThroughCenter(from, router, paths);
    }

    private void addSingleStepPalaceDiagonals(Position from, PalaceRouter router, List<MovePath> paths) {
        for (Position diagonal : router.getDiagonalAdjacents(from)) {
            directionForUnitStep(from, diagonal)
                    .ifPresent(direction -> paths.add(new MovePath(List.of(direction))));
        }
    }

    private void addTwoStepPalaceDiagonalThroughCenter(Position from, PalaceRouter router, List<MovePath> paths) {
        for (Position through : router.getDiagonalAdjacents(from)) {
            addOppositeCornerDiagonalPathIfValid(from, through, router, paths);
        }
    }

    private void addOppositeCornerDiagonalPathIfValid(
            Position from, Position through, PalaceRouter router, List<MovePath> paths) {
        Optional<Direction> direction = directionForUnitStep(from, through);
        if (direction.isEmpty()) {
            return;
        }
        addPalaceDiagonalSkipIfValid(through, direction.get(), router, paths);
    }

    private void addPalaceDiagonalSkipIfValid(
            Position through, Direction step, PalaceRouter router, List<MovePath> paths) {
        Position end = through.next(step);
        if (!end.isInsideBoard()) {
            return;
        }
        if (!router.isInsidePalace(end)) {
            return;
        }
        paths.add(new MovePath(List.of(step, step)));
    }

    private Optional<Direction> directionForUnitStep(Position from, Position to) {
        int deltaRow = to.row() - from.row();
        int deltaColumn = to.column() - from.column();

        for (Direction direction : Direction.values()) {
            if (direction.dRow() == deltaRow && direction.dColumn() == deltaColumn) {
                return Optional.of(direction);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Piece pieceAtDestination, TeamColor myTeam) {
        if (!blockingPieces.isEmpty()) {
            return false;
        }

        return pieceAtDestination == null || !pieceAtDestination.isOnTeam(myTeam);
    }


}
