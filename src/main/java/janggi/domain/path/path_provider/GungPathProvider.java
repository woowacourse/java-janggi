package janggi.domain.path.path_provider;

import janggi.domain.gung.Gung;
import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.Set;

public final class GungPathProvider implements PathProvider {

    private final Gung gung;

    public GungPathProvider(final Gung gung) {
        this.gung = gung;
    }

    @Override
    public Set<Path> get(final Position position) {
        return gung.getAllPathsFrom(position);
    }
}
