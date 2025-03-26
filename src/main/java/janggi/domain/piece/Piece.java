package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.path_filter.PathFilter;
import janggi.domain.path.path_provider.PathProvider;
import janggi.domain.position.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Piece {

    private final PieceType pieceType;
    private final List<PathProvider> pathProviders;
    private final List<PathFilter> pathFilters;

    private Position position;

    public Piece(final PieceType pieceType, final List<PathProvider> pathProviders, final List<PathFilter> pathFilters, final Position position) {
        this.pieceType = pieceType;
        this.pathProviders = pathProviders;
        this.pathFilters = pathFilters;
        this.position = position;
    }

    public void move(final Position newPosition, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final Set<Path> paths = new HashSet<>();
        for (final PathProvider pathProvider : pathProviders) {
            paths.addAll(pathProvider.get(position));
        }
        for (final PathFilter pathFilter : pathFilters) {
            pathFilter.filter(this, paths, allyPieces, enemyPieces);
        }

        if (!isNewPositionExistInMoveablePath(newPosition, paths)) {
            throw new IllegalArgumentException("움직일 수 없는 위치입니다.");
        }

        this.position = newPosition;
    }

    private boolean isNewPositionExistInMoveablePath(final Position newPosition, final Set<Path> paths) {
        return paths.stream()
                .map(Path::finalPosition)
                .toList()
                .contains(newPosition);
    }

    public Position getPosition() {
        return position;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getScore() {
        return pieceType.score;
    }
}
