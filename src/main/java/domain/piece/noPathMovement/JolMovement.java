package domain.piece.noPathMovement;

import domain.Coordinate;
import domain.MoveVector;
import domain.board.PieceSearcher;
import java.util.HashSet;
import java.util.Set;

public class JolMovement extends NoPathMovement {

    public JolMovement() {
        super(Set.of(MoveVector.UP, MoveVector.LEFT, MoveVector.RIGHT));
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        final var movements = new HashSet<>(moveVectors());
        movements.removeIf(MoveVector::isDownDirection);

        return movements.stream()
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}
