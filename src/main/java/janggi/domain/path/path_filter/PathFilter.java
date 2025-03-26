package janggi.domain.path.path_filter;

import janggi.domain.piece.Piece;
import janggi.domain.path.Path;

import java.util.List;
import java.util.Set;

public interface PathFilter {

    void filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces);
}
