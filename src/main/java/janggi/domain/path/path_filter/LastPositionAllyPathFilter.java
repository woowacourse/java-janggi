package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.Set;
import java.util.stream.Collectors;

public final class LastPositionAllyPathFilter implements PathFilter {

    @Override
    public Set<Path> filter(Set<Path> paths, PathFilterRequest request) {
        return paths.stream()
                .filter(path -> !path.isEndWith(request.allyPieces().stream().map(Piece::getPosition).toList()))
                .collect(Collectors.toSet());
    }
}
