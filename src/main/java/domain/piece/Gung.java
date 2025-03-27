package domain.piece;

import domain.Coordinate;
import domain.board.Board;
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
    public List<Coordinate> findAvailablePaths(Coordinate from, Board board) {
        movements.addMovementIfInGung(from);

        return movements.getMovements().stream()
                .map(from::move)
                .filter(Coordinate::isInGungBoundary)
                .filter(to -> !board.isMyTeam(country, to))
                .toList();
    }

}
