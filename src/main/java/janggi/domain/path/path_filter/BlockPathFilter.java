package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class BlockPathFilter implements PathFilter {

    @Override
    public Set<Path> filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        return paths.stream()
                .filter(path -> !path.isBlockedWith(getAllPositions(allyPieces, enemyPieces)))
                .collect(Collectors.toSet());
    }

    private List<Position> getAllPositions(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Position> positions = new ArrayList<>();
        positions.addAll(allyPieces.stream().map(Piece::getPosition).toList());
        positions.addAll(enemyPieces.stream().map(Piece::getPosition).toList());
        return positions;
    }
}

