package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.path.path_provider.MovementPathProvider;
import janggi.domain.position.Movement;

import java.util.Set;

public class StraightDiagonalDiagonalPathProvider extends MovementPathProvider {

    private static final Set<Movement> MOVEMENTS = Set.of(
            Movement.DOWN_DOWNLEFT_DOWNLEFT,
            Movement.DOWN_DOWNRIGHT_DOWNRIGHT,
            Movement.LEFT_DOWNLEFT_DOWNLEFT,
            Movement.LEFT_UPLEFT_UPLEFT,
            Movement.RIGHT_DOWNRIGHT_DOWNRIGHT,
            Movement.RIGHT_UPRIGHT_UPRIGHT,
            Movement.UP_UPLEFT_UPLEFT,
            Movement.UP_UPRIGHT_UPRIGHT
    );

    @Override
    protected Set<Movement> getMovements() {
        return MOVEMENTS;
    }
}
