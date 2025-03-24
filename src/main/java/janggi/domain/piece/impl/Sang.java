package janggi.domain.piece.impl;

import janggi.domain.piece.Piece;
import janggi.domain.position.Movement;
import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Sang extends Piece {

    private final List<Movement> availableMovements = List.of(
            Movement.DOWN_DOWNLEFT_DOWNLEFT,
            Movement.DOWN_DOWNRIGHT_DOWNRIGHT,
            Movement.LEFT_DOWNLEFT_DOWNLEFT,
            Movement.LEFT_UPLEFT_UPLEFT,
            Movement.RIGHT_DOWNRIGHT_DOWNRIGHT,
            Movement.RIGHT_UPRIGHT_UPRIGHT,
            Movement.UP_UPLEFT_UPLEFT,
            Movement.UP_UPRIGHT_UPRIGHT
    );

    public Sang(final Position position) {
        super(position);
    }

    @Override
    public List<Path> getMoveablePaths(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Path> availablePaths = new ArrayList<>();

        for (Movement availableMovement : availableMovements) {
            final Path path = Path.start(position).nextPath(availableMovement);
            if (!path.isBlockedWith(getPositionsOf(List.of(allyPieces, enemyPieces))) && !path.isEndedWith(getPositionsOf(List.of(allyPieces)))) {
                availablePaths.add(path);
            }
        }

        return availablePaths;
    }
}
