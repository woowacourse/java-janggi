package janggi.domain.piece;

import janggi.domain.position.Movement;
import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Jol extends Piece {

    private final List<Movement> availableMovements = List.of(
            Movement.UP,
            Movement.LEFT,
            Movement.RIGHT
    );

    public Jol(final Position position) {
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
