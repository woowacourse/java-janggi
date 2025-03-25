package janggi.domain.piece.impl;

import janggi.domain.piece.Gung;
import janggi.domain.piece.GungSpecialMovePiece;
import janggi.domain.piece.Piece;
import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Cha extends GungSpecialMovePiece {

    public Cha(final Position position, final Gung gung) {
        super(position, gung);
    }

    @Override
    protected List<Path> getMoveablePathsWithNoGung(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Path> availablePaths = new ArrayList<>();

        final List<Position> allCrossPositions = position.getAllCrossPositions();

        for (Position endPosition : allCrossPositions) {
            final Path path = Path.start(position).nextPath(endPosition);
            if (!path.isBlockedWith(getPositionsOf(List.of(allyPieces, enemyPieces))) && !path.isEndWith(getPositionsOf(List.of(allyPieces)))) {
                availablePaths.add(path);
            }
        }

        return availablePaths;
    }

    @Override
    protected List<Path> getGungSpecialPaths(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        return gung.getAllPathsFrom(position).stream()
                .filter(path -> !path.isBlockedWith(getPositionsOf(List.of(allyPieces, enemyPieces))))
                .filter(path -> !path.isEndWith(getPositionsOf(List.of(allyPieces))))
                .toList();
    }
}
