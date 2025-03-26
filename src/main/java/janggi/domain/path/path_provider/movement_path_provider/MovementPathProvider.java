package janggi.domain.path.path_provider.movement_path_provider;

import janggi.domain.path.Path;
import janggi.domain.path.path_provider.PathProvider;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class MovementPathProvider implements PathProvider {

    @Override
    public Set<Path> get(final Position position) {
        final Set<Path> paths = new HashSet<>();

        for (Movement movement : getMovements()) {
            final Optional<Path> result = Path.start(position).nextPath(movement);
            result.ifPresent(paths::add);
        }

        return paths;
    }

    protected abstract Set<Movement> getMovements();
}
