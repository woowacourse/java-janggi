package domain.piece;

import domain.Coordinate;
import domain.board.BoardContext;
import domain.piece.movement.Movement;
import domain.piece.movement.Movements;
import java.util.List;

public class Sa extends Piece {

    private final Movements movements = new Movements(
            List.of(Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT));

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public List<Coordinate> findAvailablePaths(Coordinate from, BoardContext boardContext) {
        movements.addMovementIfInGung(from);

        return movements.getMovements().stream()
                .map(from::move)
                .filter(Coordinate::isInBoundary)
                .filter(to -> !boardContext.isMyTeam(country, to))
                .toList();
    }

}
