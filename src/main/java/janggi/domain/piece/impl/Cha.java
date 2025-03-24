package janggi.domain.piece.impl;

import janggi.domain.piece.Piece;
import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    public Cha(final Position position) {
        super(position);
    }

    @Override
    public List<Path> getMoveablePaths(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Path> availablePaths = new ArrayList<>();

        final List<Position> allCrossPositions = position.getAllCrossPositions();

        for (Position endPosition : allCrossPositions) {
            final Path path = Path.start(endPosition).nextPath(endPosition);
            if (!path.isBlockedWith(getPositionsOf(List.of(allyPieces, enemyPieces))) && !path.isEndedWith(getPositionsOf(List.of(allyPieces)))) {
                availablePaths.add(path);
            }
        }

        return availablePaths;
    }
}
