package janggi.domain.path.path_filter;

import janggi.domain.piece.Piece;
import janggi.domain.path.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JumpPathFilter implements PathFilter {

    private final int jumpCount;

    public JumpPathFilter(final int jumpCount) {
        this.jumpCount = jumpCount;
    }

    @Override
    public void filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        paths.removeIf(path -> {
            final List<Piece> blockedPiece = path.getBlockedPiece(getAllPieces(allyPieces, enemyPieces));
            return blockedPiece.size() != jumpCount;
        });
    }

    private List<Piece> getAllPieces(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Piece> pieces = new ArrayList<>();
        pieces.addAll(allyPieces);
        pieces.addAll(enemyPieces);
        return pieces;
    }
}

