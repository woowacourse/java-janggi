package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.List;

public class Gung extends Piece {

    private final List<Movement> MOVEMENTS = List.of(
            Movement.UP, Movement.UP_RIGHT, Movement.RIGHT, Movement.DOWN_RIGHT,
            Movement.DOWN, Movement.DOWN_LEFT, Movement.LEFT, Movement.UP_LEFT);

    public Gung(Country country) {
        super(country, PieceType.GUNG);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate, Board board) {
        return MOVEMENTS.stream()
                .map(currCoordinate::move)
                .filter(next -> !next.isOutOfBoundary() && !board.isMyTeam(currCoordinate, next))
                .toList();
    }

}
