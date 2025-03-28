package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Set;

public interface PathFilter {

    Set<Path> filter(Piece piece, Set<Path> paths, List<Piece> allyPieces, List<Piece> enemyPieces);
}
