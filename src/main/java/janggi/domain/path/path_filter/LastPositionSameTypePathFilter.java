package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LastPositionSameTypePathFilter implements PathFilter {

    @Override
    public void filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Position> sameTypePositions = getAllPieces(allyPieces, enemyPieces)
                .stream()
                .filter(other -> other.getPieceType() == piece.getPieceType())
                .map(Piece::getPosition)
                .toList();
        paths.removeIf(path -> path.isEndWith(sameTypePositions));
    }

    private List<Piece> getAllPieces(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Piece> pieces = new ArrayList<>();
        pieces.addAll(allyPieces);
        pieces.addAll(enemyPieces);
        return pieces;
    }
}

