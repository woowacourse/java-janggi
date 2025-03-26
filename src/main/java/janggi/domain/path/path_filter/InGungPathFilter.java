package janggi.domain.path.path_filter;

import janggi.domain.piece.Gung;
import janggi.domain.piece.Piece;
import janggi.domain.path.Path;

import java.util.List;
import java.util.Set;

public class InGungPathFilter implements PathFilter {

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
