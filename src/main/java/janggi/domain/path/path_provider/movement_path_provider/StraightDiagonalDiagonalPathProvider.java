package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.position.Movement;

import java.util.Set;

public final class StraightDiagonalDiagonalPathProvider extends MovementPathProvider {

    private static final Set<Movement> MOVEMENTS = Set.of(
            Movement.UP_UPLEFT_UPLEFT,
            Movement.UP_UPRIGHT_UPRIGHT,
            Movement.DOWN_DOWNLEFT_DOWNLEFT,
            Movement.DOWN_DOWNRIGHT_DOWNRIGHT,
            Movement.LEFT_UPLEFT_UPLEFT,
            Movement.LEFT_DOWNLEFT_DOWNLEFT,
            Movement.RIGHT_UPRIGHT_UPRIGHT,
            Movement.RIGHT_DOWNRIGHT_DOWNRIGHT
    );

    @Override
    protected Set<Movement> getMovements() {
        return MOVEMENTS;
    }
}
