package janggi.domain.path.path_provider;

import janggi.domain.gungsung.Gungsung;
import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.Set;

public final class GungsungPathProvider implements PathProvider {

    private final Gungsung gungSung;

    public GungsungPathProvider(final Gungsung gungSung) {
        this.gungSung = gungSung;
    }

    @Override
    public Set<Path> get(final Position position) {
        return gungSung.getAllPathsFrom(position);
    }
}
