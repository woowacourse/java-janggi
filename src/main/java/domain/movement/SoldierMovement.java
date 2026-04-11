package domain.movement;

import domain.board.BoardState;
import domain.board.Position;
import domain.piece.Team;
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
        return movementRules().stream()
                .filter(direction -> isValidMove(source, direction))
                .toList();
    }

    private List<Direction> movementRules() {
        if (team == Team.HAN) {
            return MOVES_HAN;
        }
        return MOVES_CHO;
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