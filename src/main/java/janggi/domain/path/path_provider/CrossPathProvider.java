package janggi.domain.path.path_provider;

import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.HashSet;
import java.util.Set;

public final class CrossPathProvider implements PathProvider {

    @Override
    public Set<Path> get(final Position position) {
        final Set<Path> paths = new HashSet<>();

        for (Position endPosition : position.getAllCrossPositions()) {
            final Path path = Path.start(position).nextPath(endPosition);
            paths.add(path);
        }

        return paths;
    }
}
