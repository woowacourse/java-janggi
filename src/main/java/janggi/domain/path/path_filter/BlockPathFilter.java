package janggi.domain.path.path_filter;

import janggi.domain.path.Path;

import java.util.Set;
import java.util.stream.Collectors;

public final class BlockPathFilter implements PathFilter {

    @Override
    public Set<Path> filter(final Set<Path> paths, final PathFilterRequest request) {
        return paths.stream()
                .filter(path -> !path.isBlockedWith(request.allPositions()))
                .collect(Collectors.toSet());
    }
}

