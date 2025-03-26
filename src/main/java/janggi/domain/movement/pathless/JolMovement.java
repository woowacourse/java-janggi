package janggi.domain.movement.pathless;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.MoveUnit;
import janggi.domain.movement.MoveVector;
import java.util.Set;

public class JolMovement extends PathlessMovement {

    public JolMovement() {
        super(Set.of(
            new MoveVector(MoveUnit.LEFT),
            new MoveVector(MoveUnit.RIGHT),
            new MoveVector(MoveUnit.UP)
        ));
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        return moveVectorsAt(departure).stream()
            .map(MoveVector::first)
            .filter(MoveUnit::isUpDirection)
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}
