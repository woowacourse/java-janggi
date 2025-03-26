package janggi.domain.path.path_filter;

import janggi.domain.gung.Gung;
import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Set;

public final class InGungPathFilter implements PathFilter {

    private final Gung gung;

    public InGungPathFilter(final Gung gung) {
        this.gung = gung;
    }

    @Override
    public void filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        paths.removeIf(path -> !gung.isInGung(path));
        paths.removeIf(path -> !gung.isAvailablePathInGung(path));
    }
}
