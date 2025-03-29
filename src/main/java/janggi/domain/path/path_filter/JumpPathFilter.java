package janggi.domain.path.path_filter;

import janggi.domain.path.Path;
import janggi.domain.piece.Piece;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JumpPathFilter implements PathFilter {

    private final int jumpCount;

    public JumpPathFilter(final int jumpCount) {
        this.jumpCount = jumpCount;
    }

    @Override
    public Set<Path> filter(final Set<Path> paths, final PathFilterRequest request) {
        return paths.stream()
                .filter(path -> {
                    final List<Piece> blockedPiece = path.getBlockedPiece(request.allPieces());
                    return blockedPiece.size() == jumpCount;
                })
                .collect(Collectors.toSet());
    }
}

