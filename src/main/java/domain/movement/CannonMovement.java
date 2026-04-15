package domain.movement;

import domain.board.BoardState;
import domain.board.Position;
import domain.movement.palace.Palace;
import domain.movement.vo.Delta;
import domain.movement.vo.Direction;
import domain.movement.vo.Path;
import domain.movement.vo.Paths;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class CannonMovement implements Movement {
    private static final List<Direction> MOVEMENT_RULES = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );
    private static final int REQUIRED_JUMP_COUNT = 1;

    @Override
    public Paths findPotentialPaths(Position source) {
        List<Path> straightPaths = MOVEMENT_RULES.stream()
                .flatMap(direction -> generatePathsInDirection(source, direction).stream())
                .toList();
        List<Path> diagonalPaths = createPalaceDiagonalPaths(source);
        List<Path> allPaths = Stream.concat(straightPaths.stream(), diagonalPaths.stream())
                .toList();

        return new Paths(allPaths);
    }

    private List<Path> generatePathsInDirection(Position source, Direction direction) {
        List<Path> paths = new ArrayList<>();
        Position current = source;
        List<Position> route = new ArrayList<>();
        Delta delta = direction.delta();

        while (current.canShift(delta)) {
            current = current.shift(delta);
            route.add(current);
            paths.add(createPath(route));
        }

        return paths;
    }

    private Path createPath(List<Position> route) {
        return new Path(new ArrayList<>(route));
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
        List<Position> obstacles = findObstacles(path, board);

        if (obstacles.size() != REQUIRED_JUMP_COUNT) {
            return false;
        }

        Position bridge = obstacles.getFirst();
        Piece piece = board.pieceAt(bridge);
        if (piece.getPieceType() == PieceType.CANNON) {
            return false;
        }

        return isValidDestination(path.destination(), board);
    }

    private List<Position> findObstacles(Path path, BoardState board) {
        return path.positionsBeforeDestination().stream()
                .filter(position -> isOccupied(position, board))
                .toList();
    }

    private boolean isOccupied(Position position, BoardState board) {
        return !board.isEmpty(position);
    }

    private boolean isValidDestination(Position destination, BoardState board) {
        if (board.isEmpty(destination)) {
            return true;
        }

        Piece piece = board.pieceAt(destination);
        return piece.getPieceType() != PieceType.CANNON;
    }
}
