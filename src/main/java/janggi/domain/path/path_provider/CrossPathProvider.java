package janggi.domain.path.path_provider;

import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.Set;
import java.util.stream.Collectors;

public final class CrossPathProvider implements PathProvider {

    @Override
    public Set<Path> get(final Position position) {
        return position.getAllCrossPositions().stream()
                .map(endPosition -> Path.start(position).nextPath(endPosition))
                .collect(Collectors.toSet());
    }
}
