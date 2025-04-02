package domain.piece;

import domain.piece.coordiante.Coordinate;
import domain.board.ReadableBoard;
import domain.piece.movement.Movement;
import domain.piece.movement.Movements;
import java.util.List;

public class Gung extends Piece {

    private final Movements movements = new Movements(
            List.of(Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT));

    public Gung(Country country) {
        super(country, PieceType.GUNG);
    }

    @Override
    public List<Coordinate> findAvailablePaths(Coordinate from, ReadableBoard readableBoard) {
        movements.addMovementIfInGung(from);

        return movements.getMovements().stream()
                .map(from::move)
                .filter(Coordinate::isInGungBoundary)
                .filter(to -> !readableBoard.isMyTeam(country, to))
                .toList();
    }

}
