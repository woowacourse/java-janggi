package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.path.Path;
import janggi.domain.path.path_provider.PathProvider;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class MovementPathProvider implements PathProvider {

    @Override
    public Set<Path> get(final Position position) {
        return getMovements().stream()
                .map(movement -> Path.start(position).nextPath(movement))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    protected abstract Set<Movement> getMovements();
}
