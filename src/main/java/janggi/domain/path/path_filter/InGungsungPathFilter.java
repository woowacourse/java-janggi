package janggi.domain.path.path_filter;

import janggi.domain.gungsung.Gungsung;
import janggi.domain.path.Path;

import java.util.Set;
import java.util.stream.Collectors;

public final class InGungsungPathFilter implements PathFilter {

    private final Gungsung gungSung;

    public InGungsungPathFilter(final Gungsung gungSung) {
        this.gungSung = gungSung;
    }

    @Override
    public Set<Path> filter(final Set<Path> paths, final PathFilterRequest request) {
        return paths.stream()
                .filter(gungSung::isInGung)
                .filter(gungSung::isAvailablePathInGung)
                .collect(Collectors.toSet());
    }
}
