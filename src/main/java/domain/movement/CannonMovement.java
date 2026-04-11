package domain.movement;

import domain.board.BoardState;
import domain.board.Position;
import domain.movement.vo.Delta;
import domain.movement.vo.Direction;
import domain.movement.vo.Path;
import domain.movement.vo.Paths;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;

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
        List<Path> allPaths = MOVEMENT_RULES.stream()
                .flatMap(direction -> generatePathsInDirection(source, direction).stream())
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

    @Override
    public boolean isAvailablePath(Path path, BoardState board) {
        List<Position> obstacles = findObstacles(path, board);

        if (!hasValidJumpCount(obstacles.size())) {
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

    private boolean hasValidJumpCount(int obstacleCount) {
        return obstacleCount == REQUIRED_JUMP_COUNT;
    }

    private boolean isValidDestination(Position destination, BoardState board) {
        if (board.isEmpty(destination)) {
            return true;
        }

        Piece piece = board.pieceAt(destination);
        return piece.getPieceType() != PieceType.CANNON;
    }
}