package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class BlockSameTypePathFilter implements PathFilter {

    @Override
    public Set<Path> filter(final Piece piece, final Set<Path> paths, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        return paths.stream()
                .filter(path -> {
                    final List<Piece> blockedPiece = path.getBlockedPiece(getAllPieces(allyPieces, enemyPieces));
                    return blockedPiece.stream().allMatch(target -> target.getPieceType() != piece.getPieceType());
                })
                .collect(Collectors.toSet());
    }
    
    private List<Piece> getAllPieces(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Piece> pieces = new ArrayList<>();
        pieces.addAll(allyPieces);
        pieces.addAll(enemyPieces);
        return pieces;
    }
}

