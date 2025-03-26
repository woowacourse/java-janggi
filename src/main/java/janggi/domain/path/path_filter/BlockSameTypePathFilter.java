package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockSameTypePathFilter implements PathFilter {

    @Override
    public void filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        paths.removeIf(path -> {
            final List<Piece> blockedPiece = path.getBlockedPiece(getAllPieces(allyPieces, enemyPieces));
            return blockedPiece.stream().anyMatch(target -> target.getPieceType() == piece.getPieceType());
        });
    }

    private List<Piece> getAllPieces(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Piece> pieces = new ArrayList<>();
        pieces.addAll(allyPieces);
        pieces.addAll(enemyPieces);
        return pieces;
    }
}

