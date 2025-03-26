package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.position.Movement;

import java.util.Set;

public final class DownLeftRightPathProvider extends MovementPathProvider {

    private static final Set<Movement> MOVEMENTS = Set.of(
            Movement.DOWN,
            Movement.LEFT,
            Movement.RIGHT
    );

    @Override
    protected Set<Movement> getMovements() {
        return MOVEMENTS;
    }
}
