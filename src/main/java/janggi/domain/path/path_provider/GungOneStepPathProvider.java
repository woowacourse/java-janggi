package janggi.domain.path.path_provider;

import janggi.domain.gung.Gung;
import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.Set;
import java.util.stream.Collectors;

public final class GungOneStepPathProvider implements PathProvider {

    private final Gung gung;

    public GungOneStepPathProvider(final Gung gung) {
        this.gung = gung;
    }

    @Override
    public Set<Path> get(final Position position) {
        return gung.getAllPathsFrom(position).stream()
                .filter(path -> path.getMoveCount() == 1)
                .collect(Collectors.toSet());
    }
}
