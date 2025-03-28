package janggi.domain.path.path_filter;

import janggi.domain.gung.Gung;
import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class InGungPathFilter implements PathFilter {

    private final Gung gung;

    public InGungPathFilter(final Gung gung) {
        this.gung = gung;
    }

    @Override
    public Set<Path> filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        return paths.stream()
                .filter(gung::isInGung)
                .filter(gung::isAvailablePathInGung)
                .collect(Collectors.toSet());
    }
}
