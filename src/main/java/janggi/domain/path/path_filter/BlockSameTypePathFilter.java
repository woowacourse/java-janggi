package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class BlockSameTypePathFilter implements PathFilter {

    @Override
    public Set<Path> filter(final Set<Path> paths, final PathFilterRequest request) {
        return paths.stream()
                .filter(path -> {
                    final List<Piece> blockedPiece = path.getBlockedPiece(request.allPieces());
                    return blockedPiece.stream().allMatch(target -> target.getPieceType() != request.piece().getPieceType());
                })
                .collect(Collectors.toSet());
    }
}

