package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.List;

public abstract class InGungPiece extends Piece {

    private final Gung gung;

    public InGungPiece(final Position position, final Gung gung) {
        super(position);
        this.gung = gung;
    }

    @Override
    protected List<Path> getMoveablePaths(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Path> paths = getMoveablePathsWithNoGung(allyPieces, enemyPieces);
        return paths.stream()
                .filter(gung::isInGung)
                .filter(gung::isAvailablePath)
                .toList();
    }

    protected abstract List<Path> getMoveablePathsWithNoGung(final List<Piece> allyPieces, final List<Piece> enemyPieces);
}
