package janggi.domain.path.path_filter;

import janggi.domain.path.Path;

import java.util.Set;
import java.util.stream.Collectors;

public final class LastPositionSameTypePathFilter implements PathFilter {

    @Override
    public Set<Path> filter(Set<Path> paths, PathFilterRequest request) {
        return paths.stream()
                .filter(path -> !path.isEndWith(request.sameTypePositions()))
                .collect(Collectors.toSet());
    }
}

