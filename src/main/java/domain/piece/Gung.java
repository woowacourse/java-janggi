package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.List;

public class Gung extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            Movement.UP, Movement.RIGHT, Movement.DOWN, Movement.LEFT);

    public Gung(Country country) {
        super(country, PieceType.GUNG);
    }

    @Override
    public List<Coordinate> findAvailablePaths(Coordinate from, Board board) {
        from.addGungMovement(MOVEMENTS);
        return MOVEMENTS.stream()
                .map(from::move)
                .filter(Coordinate::isInBoundary)
                .filter(to -> !board.isMyTeam(country, to))
                .toList();
    }

}
