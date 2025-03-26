package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Set;

public final class LastPositionAllyPathFilter implements PathFilter {

    @Override
    public void filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        paths.removeIf(path -> path.isEndWith(allyPieces.stream().map(Piece::getPosition).toList()));
    }
}
