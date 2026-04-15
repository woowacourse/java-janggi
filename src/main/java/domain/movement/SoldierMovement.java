package domain.movement;

import domain.board.BoardState;
import domain.board.Position;
import domain.movement.palace.Palace;
import domain.movement.vo.Direction;
import domain.movement.vo.Path;
import domain.movement.vo.Paths;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;

public final class SoldierMovement implements Movement {
    private static final List<Direction> MOVES_HAN = List.of(
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );
    private static final List<Direction> MOVES_CHO = List.of(
            Direction.UP,
            Direction.LEFT,
            Direction.RIGHT
    );

    private final Team team;

    public SoldierMovement(Team team) {
        this.team = team;
    }

    @Override
    public Paths findPotentialPaths(Position source) {
        List<Direction> validDirections = filterValidDirections(source);
        return parseDirectionsToPaths(source, validDirections);
    }

    private List<Direction> filterValidDirections(Position source) {
        return movementRules(source).stream()
                .filter(direction -> isValidMove(source, direction))
                .toList();
    }

    private List<Direction> movementRules(Position source) {
        List<Direction> rules = new ArrayList<>(forwardAndSidewaysRules());
        if (Palace.isInside(source)) {
            rules.addAll(palaceDiagonalRules());
        }
        return rules;
    }

    private List<Direction> forwardAndSidewaysRules() {
        if (team == Team.HAN) {
            return MOVES_HAN;
        }
        return MOVES_CHO;
    }

    private List<Direction> palaceDiagonalRules() {
        if (team == Team.HAN) {
            return List.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT);
        }
        return List.of(Direction.UP_LEFT, Direction.UP_RIGHT);
    }

    private boolean isValidMove(Position source, Direction direction) {
        if (!source.canShift(direction.delta())) {
            return false;
        }
        if (!isDiagonal(direction)) {
            return true;
        }
        Position destination = source.shift(direction.delta());
        if (!Palace.isInside(destination)) {
            return false;
        }
        return isPalaceDiagonal(source, destination);
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

    private boolean isDiagonal(Direction direction) {
        return direction.delta().columnDelta() != 0 && direction.delta().rowDelta() != 0;
    }

    private boolean isPalaceDiagonal(Position source, Position destination) {
        return isCenterToCorner(source, destination) || isCenterToCorner(destination, source);
    }

    private boolean isCenterToCorner(Position source, Position destination) {
        return Palace.isCenter(source) && Palace.isCorner(destination);
    }
}
