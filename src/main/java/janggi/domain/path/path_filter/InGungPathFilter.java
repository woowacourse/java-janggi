package janggi.domain.path.path_filter;

import janggi.domain.gung.Gung;
import janggi.domain.path.Path;

import java.util.Set;
import java.util.stream.Collectors;

public final class InGungPathFilter implements PathFilter {

    private final Gung gung;

    public InGungPathFilter(final Gung gung) {
        this.gung = gung;
    }

    @Override
    public Set<Path> filter(final Set<Path> paths, final PathFilterRequest request) {
        return paths.stream()
                .filter(gung::isInGung)
                .filter(gung::isAvailablePathInGung)
                .collect(Collectors.toSet());
    }
}
