package janggi.domain.piece.impl;

import janggi.domain.piece.Piece;
import janggi.domain.position.Movement;
import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Ma extends Piece {

    private final List<Movement> availableMovements = List.of(
            Movement.UP_UPLEFT,
            Movement.UP_UPRIGHT,
            Movement.LEFT_UPLEFT,
            Movement.LEFT_DOWNLEFT,
            Movement.RIGHT_DOWNRIGHT,
            Movement.RIGHT_UPRIGHT,
            Movement.DOWN_DOWNLEFT,
            Movement.DOWN_DOWNRIGHT
    );

    public Ma(final Position position) {
        super(position);
    }

    @Override
    public List<Path> getMoveablePaths(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
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
