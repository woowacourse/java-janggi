package strategy.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.palace.PalaceRouter;

public class CannonMoveStrategy extends MoveStrategy {

    @Override
    public List<MovePath> getPaths(Piece piece, Position from, PalaceRouter router) {
        List<MovePath> paths = new ArrayList<>();

        paths.addAll(createStraightPaths(Direction.NORTH));
        paths.addAll(createStraightPaths(Direction.SOUTH));
        paths.addAll(createStraightPaths(Direction.EAST));
        paths.addAll(createStraightPaths(Direction.WEST));
        paths.addAll(createPalaceDiagonalPaths(from, router));

        return paths;
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

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Piece pieceAtDestination, TeamColor myTeam) {
        if (blockingPieces.size() != 1) {
            return false;
        }

        Piece bridgePiece = blockingPieces.getFirst();
        if (bridgePiece.getPieceType() == PieceType.CANNON) {
            return false;
        }

        if (pieceAtDestination == null) {
            return true;
        }

        if (pieceAtDestination.getPieceType() == PieceType.CANNON) {
            return false;
        }

        return !pieceAtDestination.isOnTeam(myTeam);
    }
}
