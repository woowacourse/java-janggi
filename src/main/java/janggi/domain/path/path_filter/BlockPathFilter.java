package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockPathFilter implements PathFilter {

    @Override
    public void filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        paths.removeIf(path -> path.isBlockedWith(getAllPositions(allyPieces, enemyPieces)));
    }

    private List<Position> getAllPositions(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Position> positions = new ArrayList<>();
        positions.addAll(allyPieces.stream().map(Piece::getPosition).toList());
        positions.addAll(enemyPieces.stream().map(Piece::getPosition).toList());
        return positions;
    }
}

