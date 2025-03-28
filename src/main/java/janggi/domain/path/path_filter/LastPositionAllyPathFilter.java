package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class LastPositionAllyPathFilter implements PathFilter {

    @Override
    public Set<Path> filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        return paths.stream()
                .filter(path -> !path.isEndWith(allyPieces.stream().map(Piece::getPosition).toList()))
                .collect(Collectors.toSet());
    }
}
