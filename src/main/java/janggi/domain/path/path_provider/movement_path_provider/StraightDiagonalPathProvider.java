package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.path.path_provider.MovementPathProvider;
import janggi.domain.position.Movement;

import java.util.Set;

public class StraightDiagonalPathProvider extends MovementPathProvider {

    private static final Set<Movement> MOVEMENTS = Set.of(
            Movement.UP_UPLEFT,
            Movement.UP_UPRIGHT,
            Movement.LEFT_UPLEFT,
            Movement.LEFT_DOWNLEFT,
            Movement.RIGHT_DOWNRIGHT,
            Movement.RIGHT_UPRIGHT,
            Movement.DOWN_DOWNLEFT,
            Movement.DOWN_DOWNRIGHT
    );

    @Override
    protected Set<Movement> getMovements() {
        return MOVEMENTS;
    }
}
