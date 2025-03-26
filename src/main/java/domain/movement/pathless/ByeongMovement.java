package domain.movement.pathless;

import domain.Coordinate;
import domain.board.PieceSearcher;
import domain.movement.MoveUnit;
import domain.movement.MoveVector;
import java.util.Set;

public class ByeongMovement extends PathlessMovement {

    public ByeongMovement() {
        super(Set.of(
            new MoveVector(MoveUnit.LEFT),
            new MoveVector(MoveUnit.RIGHT),
            new MoveVector(MoveUnit.DOWN)
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
            .filter(MoveUnit::isDownDirection)
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}
