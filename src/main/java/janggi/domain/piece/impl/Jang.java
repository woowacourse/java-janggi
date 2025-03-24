package janggi.domain.piece.impl;

import janggi.domain.piece.Gung;
import janggi.domain.piece.InGungPiece;
import janggi.domain.piece.Piece;
import janggi.domain.position.Movement;
import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Jang extends InGungPiece {

    private final List<Movement> availableMovements = List.of(
            Movement.UP,
            Movement.LEFT,
            Movement.RIGHT,
            Movement.DOWN,
            Movement.UP_LEFT,
            Movement.UP_RIGHT,
            Movement.DOWN_LEFT,
            Movement.DOWN_RIGHT
    );

    public Jang(final Position position, final Gung gung) {
        super(position, gung);
    }

    @Override
    public List<Path> getMoveablePathsWithNoGung(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Path> availablePaths = new ArrayList<>();

        for (Movement availableMovement : availableMovements) {
            final Path path = Path.start(position).nextPath(availableMovement);
            if (!path.isBlockedWith(getPositionsOf(List.of(allyPieces, enemyPieces))) && !path.isEndWith(getPositionsOf(List.of(allyPieces)))) {
                availablePaths.add(path);
            }
        }

        return availablePaths;
    }
}
