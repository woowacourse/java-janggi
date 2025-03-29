package janggi.domain.path.path_provider;

import janggi.domain.gungsung.Gungsung;
import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.Set;
import java.util.stream.Collectors;

public final class GungsungOneStepPathProvider implements PathProvider {

    private final Gungsung gungSung;

    public GungsungOneStepPathProvider(final Gungsung gungSung) {
        this.gungSung = gungSung;
    }

    @Override
    public Set<Path> get(final Position position) {
        return gungSung.getAllPathsFrom(position).stream()
                .filter(path -> path.getMoveCount() == 1)
                .collect(Collectors.toSet());
    }
}
