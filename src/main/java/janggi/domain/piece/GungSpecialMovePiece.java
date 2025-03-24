package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class GungSpecialMovePiece extends Piece {

    protected final Gung gung;

    protected GungSpecialMovePiece(final Position position, final Gung gung) {
        super(position);
        this.gung = gung;
    }

    @Override
    protected List<Path> getMoveablePaths(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Path> paths = new ArrayList<>();
        paths.addAll(getMoveablePathsWithNoGung(allyPieces, enemyPieces));
        paths.addAll(getGungSpecialPaths(allyPieces, enemyPieces));
        return paths.stream()
                .filter(gung::isAvailablePathInGung)
                .toList();
    }

    protected abstract List<Path> getMoveablePathsWithNoGung(final List<Piece> allyPieces, final List<Piece> enemyPieces);

    protected abstract List<Path> getGungSpecialPaths(final List<Piece> allyPieces, final List<Piece> enemyPieces);
}
